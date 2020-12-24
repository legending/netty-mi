package com.legend.nettyim.netty.business;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HtmlUtil;
import com.alibaba.fastjson.JSON;
import com.legend.nettyim.common.util.ApiResult;
import com.legend.nettyim.common.util.IMRequest;
import com.legend.nettyim.entity.Msgrecord;
import com.legend.nettyim.mapper.MsgrecordMapper;
import com.legend.nettyim.netty.GroupChannelManager;
import com.legend.nettyim.netty.MsgProtocol;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class MsgProc {

    @Autowired
    private MsgrecordMapper msgrecordMapper;

    //处理组消息
     public ApiResult groupMsg(ChannelHandlerContext ctx, IMRequest request){
                Map resultMap=new HashMap();
                Msgrecord msgrecord=new Msgrecord();
                //防止xss攻击
                request.getParam().put("content",HtmlUtil.filter((String) request.getParam().get("content")));
                BeanUtil.fillBeanWithMap(request.getParam(),msgrecord,true,true);
                msgrecord.setCreateTime(new Date());
                 InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
                 String clientIP = insocket.getAddress().getHostAddress();
                msgrecord.setIp(clientIP);
                msgrecordMapper.insertSelective(msgrecord);
                ApiResult apiResult=null;
                resultMap.put(MsgProtocol.quan_min_ga_liao,msgrecord);
                resultMap.put("param",request.getParam());
                apiResult= ApiResult.success(resultMap);

                 apiResult.setUrl(request.getUrl());
               for (Channel channel : GroupChannelManager.getAllChannel()) {
                        //                        if (channel != ctx.channel()){
                        //                            channel.writeAndFlush(new TextWebSocketFrame(channel.remoteAddress()+"  "+request));
                        //                        }else {
                        //                            channel.writeAndFlush(new TextWebSocketFrame("我是菜鸡服务器:"+"  "+request ));
                        //                        }


                   channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(apiResult)));
               }


       //  GroupChannelManager.getAllChannel().find()

                return apiResult;
    };

}
