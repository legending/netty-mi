package com.legend.nettyim.netty.business;

import com.alibaba.fastjson.JSON;
import com.legend.nettyim.common.util.ApiResult;
import com.legend.nettyim.common.util.IMRequest;
import com.legend.nettyim.netty.GroupChannelManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Component;

//用户相关处理逻辑
@Component
public class UserProc {

    //新用户建立连接
  public   ApiResult getLineNum(ChannelHandlerContext ctx, IMRequest request){
        ApiResult result=ApiResult.success();
        result.setUrl(request.getUrl());
        result.setData(GroupChannelManager.getAllChannel().size());
        ctx.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(result)));
        return  result;
    }
}
