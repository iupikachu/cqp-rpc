package com.cqp.cqprpc.client;

import com.cqp.cqprpc.common.Codec.MessageCodecSharable;
import com.cqp.cqprpc.common.Codec.ProcotolFrameDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName RpcClientProxy.java
 * @Description 动态代理
 * @createTime 2021年11月14日 12:49:00
 */
@Slf4j
public class RpcClientProxy {
    private String ip_address;
    private int port;
    private static Channel channel;
    


    private static void initChannel() {

            NioEventLoopGroup group = new NioEventLoopGroup();
            LoggingHandler loggingHandler = new LoggingHandler(LogLevel.DEBUG);
            MessageCodecSharable messageCodec = new MessageCodecSharable();

            // 待实现 RpcResponse 处理器
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.group(group);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ProcotolFrameDecoder());
                    ch.pipeline().addLast(loggingHandler);
                    ch.pipeline().addLast(messageCodec);
                    // 还需增加 RpcResponseHandle
                }
            });

        try {
            channel = bootstrap.connect("localhost",8080).sync().channel();
            channel.closeFuture().sync();   // 阻塞住，通道关闭再往下执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
    }
}
