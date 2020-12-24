package com.legend.nettyim.netty;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.legend.nettyim.common.util.ApiResult;
import com.legend.nettyim.common.util.SpringUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.concurrent.ThreadPoolExecutor;

import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import static io.netty.handler.codec.rtsp.RtspResponseStatuses.BAD_REQUEST;

public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {

    private static final Logger log = LoggerFactory.getLogger(WebSocketServerHandler.class);


    private WebSocketServerHandshaker handshaker;

    //工作线程组
    private static ThreadPoolExecutor workPool = ThreadUtil.newExecutor(10, 20);


    private MsgRoute msgRoute = (MsgRoute) SpringUtil.getBean("msgRoute");

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.debug("channelRead0:" + Thread.currentThread().getName());
        // 传统的HTTP接入
        if (msg instanceof FullHttpRequest) {
            handleHttpRequest(ctx, (FullHttpRequest) msg);
        }
        // WebSocket接入
        else if (msg instanceof WebSocketFrame) {
            handleWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.debug("channelReadComplete:" + Thread.currentThread().getName());
        ctx.flush();
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
        log.debug("handleHttpRequest:" + Thread.currentThread().getName());
        // 如果HTTP解码失败，返回HHTP异常
        if (!req.getDecoderResult().isSuccess() || (!"websocket".equals(req.headers().get("Upgrade")))) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST));
            return;
        }

        // 构造握手响应返回，本机测试
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("ws://localhost:1949/222websocket/2222", null, false);
        handshaker = wsFactory.newHandshaker(req);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handshaker.handshake(ctx.channel(), req);
        }
    }

    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        log.debug("handleWebSocketFrame:" + Thread.currentThread().getName());
        // 判断是否是关闭链路的指令
        if (frame instanceof CloseWebSocketFrame) {
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return;
        }
        // 判断是否是Ping消息
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        // 本例程仅支持文本消息，不支持二进制消息
        if (!(frame instanceof TextWebSocketFrame)) {
            throw new UnsupportedOperationException(
                    String.format("%s frame types not supported", frame.getClass().getName()));
        }

        // 返回应答消息
        String request = ((TextWebSocketFrame) frame).text();

        log.info(String.format("%s received %s", ctx.channel(), request));

        if (StrUtil.isNotEmpty(request)) {

            WorkRunable workRunable = new WorkRunable(ctx, request);
            workPool.execute(workRunable);
        }

    }

    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res) {
        log.debug("sendHttpResponse:" + Thread.currentThread().getName());
        // 返回应答给客户端
        if (res.getStatus().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
            HttpUtil.setContentLength(res, res.content().readableBytes());

        }

        // 如果是非Keep-Alive，关闭连接
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!HttpUtil.isKeepAlive(req) || res.status().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("连接异常:exceptionCaught:", cause);
        //ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
        log.debug("建立连接channelActive:" + Thread.currentThread().getName());
        GroupChannelManager.getAllChannel().add(ctx.channel());
        ApiResult apiResult = ApiResult.success();
        apiResult.setUrl(MsgProtocol.new_connect);
        apiResult.setData(GroupChannelManager.getAllChannel().size());

        for (Channel channel : GroupChannelManager.getAllChannel()) {

            channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(apiResult)));
        }
        // GroupChannelManager.getAllChannel()
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
        log.debug("关闭连接channelInactive:" + Thread.currentThread().getName());
        GroupChannelManager.getAllChannel().remove(ctx.channel());
        ctx.close();

        ApiResult apiResult = ApiResult.success();
        apiResult.setUrl(MsgProtocol.del_connect);
        apiResult.setData(GroupChannelManager.getAllChannel().size());

        for (Channel channel : GroupChannelManager.getAllChannel()) {

            channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(apiResult)));
        }
    }

    class WorkRunable implements Runnable {

        private ChannelHandlerContext ctx;
        private String request;

        public WorkRunable(ChannelHandlerContext ctx, String request) {
            this.ctx = ctx;
            this.request = request;
        }

        @Override
        public void run() {
            msgRoute.distribute(ctx, request);
        }
    }


}


