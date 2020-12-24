package com.legend.nettyim.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component      //被 spring 容器管理
//@Order(2)     //如果多个自定义的 ApplicationRunner  ，用来标明执行的顺序
public class NettyServer implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(NettyServer.class);

    @Value("${websocket.port}")
    private String port;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.debug("-------------->" + "项目启动，now=" + new Date());
        log.debug("获取到的参数： " + args.getOptionNames());
        log.debug("获取到的参数： " + args.getNonOptionArgs());
        log.debug("获取到的参数： " + args.getSourceArgs());
        startServer(args);
    }

    public void startServer(ApplicationArguments args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast("http-codec", new HttpServerCodec());
                    pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
                    ch.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
                    pipeline.addLast("handler", new WebSocketServerHandler());
                }
            });
            //Thread.sleep(1000*40);

            ChannelFuture future = b.bind(Integer.valueOf(port));  //第一条线程从这里开始创建
            future = future.sync();
            Channel ch = future.channel();
            // Channel ch = b.bind(Integer.valueOf(port)).sync().channel();
            log.info("Web socket server started at port " + port + '.');


            ch.closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
