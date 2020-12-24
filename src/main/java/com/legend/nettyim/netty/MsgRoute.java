package com.legend.nettyim.netty;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.legend.nettyim.common.util.ApiResult;
import com.legend.nettyim.common.util.Constants.Constants;
import com.legend.nettyim.common.util.IMRequest;
import com.legend.nettyim.common.util.RedisUtil;
import com.legend.nettyim.entity.Visitor;
import com.legend.nettyim.netty.business.MsgProc;
import com.legend.nettyim.netty.business.UserProc;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.Date;

@Component
public class MsgRoute {
    @Autowired
    private MsgProc msgProc;
    @Autowired
    private UserProc userProc;

    //路由分配,暂时写死,后期可以注解反射来实现
    public ApiResult distribute(ChannelHandlerContext ctx, String request) {

        //安全防护
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIP = insocket.getAddress().getHostAddress();
        String bv = RedisUtil.hget(Constants.blackMap, clientIP);
        if (!StrUtil.isEmpty(bv)) {
            ApiResult result = ApiResult.fail("你已被列入黑名单,无法访问系统");
            ctx.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(result)));
            return result;
        }

        String obj = RedisUtil.hget(Constants.accessTimeMap, clientIP); //上次访问时间
        Visitor visitor = null;
        if (StrUtil.isNotEmpty(obj)) {
            visitor = JSON.parseObject(obj, Visitor.class);
            //超过非法次数后,直接拉黑
            if (visitor.getIllegalCount() >= Constants.illegalCount) {
                RedisUtil.hset(Constants.blackMap, clientIP, "1");
                Date now = new Date();
                long dv = DateUtil.between(DateUtil.parse(visitor.getLastTime()).toJdkDate(), now, DateUnit.SECOND);
                if (dv <= Constants.timeInterval) {
                    visitor.setIllegalCount(visitor.getIllegalCount() + 1);
                }
            }

        } else {
            visitor = new Visitor();
            visitor.setIp(clientIP);
            visitor.setIllegalCount(0);
        }

        //重新写入visitor
        visitor.setLastTime(DateUtil.now().toString());

        RedisUtil.hset(Constants.accessTimeMap, clientIP, JSON.toJSONString(visitor));


        IMRequest imreq = JSON.parseObject(request, IMRequest.class);
        ApiResult result = null;
        //群消息
        if (imreq.getUrl().equals(MsgProtocol.quan_min_ga_liao)) {
            result = msgProc.groupMsg(ctx, imreq);
            return result;
        }

        if (imreq.getUrl().equals(MsgProtocol.get_line_num)) {
            result = userProc.getLineNum(ctx, imreq);
            return result;
        }
        return null;
    }
}
