package com.cqp.cqprpc.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.nio.charset.Charset;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName RpcServer.java
 * @Description netty 实现rpc服务端
 * @createTime 2021年11月12日 09:24:00
 */
public class RpcServer extends Server{
    NioEventLoopGroup boss;
    NioEventLoopGroup worker;
    ServerBootstrap bootstrap;
    Channel channel;
    public RpcServer(int port) {
        super(port);
    }

    @Override
    public void start() {
       boss = new NioEventLoopGroup(1);
       worker = new NioEventLoopGroup();
       bootstrap = new ServerBootstrap();
       bootstrap.channel(NioServerSocketChannel.class);
       bootstrap.group(boss, worker);
       LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);

        bootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ch.pipeline().addLast(LOGGING_HANDLER);

            }
        });

        try {
            channel = bootstrap.bind(port).sync().channel();
            channel.closeFuture().sync();   // 阻塞住，不然会直接执行 finally 的关闭代码
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }

    @Override
    public void stop() {
        try {
            channel.close();
        } catch (Exception e) {
            throw new RuntimeException("RpcServer 正常关闭失败",e);
        }
    }
}
