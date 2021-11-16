package com.cqp.cqprpc.server;

import com.cqp.cqprpc.common.Codec.MessageCodecSharable;
import com.cqp.cqprpc.common.Codec.ProcotolFrameDecoder;
import com.cqp.cqprpc.server.handler.RpcRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

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

       LoggingHandler loggingHandler = new LoggingHandler(LogLevel.DEBUG);
       MessageCodecSharable messageCodec = new MessageCodecSharable();
       RpcRequestHandler rpcRequestHandler = new RpcRequestHandler();
       bootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ch.pipeline().addLast(new ProcotolFrameDecoder());
                ch.pipeline().addLast(loggingHandler);
                ch.pipeline().addLast(messageCodec);
                ch.pipeline().addLast(rpcRequestHandler);
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
