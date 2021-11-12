package com.cqp.cqprpc.client;

import com.cqp.cqprpc.Message.RpcRequest;
import com.cqp.cqprpc.common.Codec.MessageCodecSharable;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName RpcClient.java
 * @Description rpc客户端
 * @createTime 2021年11月12日 14:48:00
 */
@Slf4j
public class RpcClient extends Client{
    public RpcClient(String ip_address, int port) {
        super(ip_address, port);
    }

    private Channel channel;
    private NioEventLoopGroup group;

    @Override
    public void start() {
        try {
            group = new NioEventLoopGroup();
            LoggingHandler loggingHandler = new LoggingHandler(LogLevel.DEBUG);
            MessageCodecSharable messageCodec = new MessageCodecSharable();

            // 待实现 RpcResponse 处理器
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.group(group);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    //ch.pipeline().addLast(new ProcotolFrameDecoder());  后续增加
                    ch.pipeline().addLast(loggingHandler);
                    ch.pipeline().addLast(messageCodec);
                    // 还需增加 RpcResponseHandle

                }
            });
            channel = bootstrap.connect(this.getIp_address(), this.getPort()).sync().channel();

            // 模拟测试发送一个 rpc-request
            channel.writeAndFlush(
                    new RpcRequest("UserService","getUser", String.class,new Class[]{Integer.class,String.class},new Object[]{1,"a"})
            ).addListener(promise ->{ // 异步检测 channel 是否发送成功
                if(!promise.isSuccess()){
                    Throwable cause = promise.cause();
                    log.error("error",cause);
                }
            });



            channel.closeFuture().sync();   // 阻塞住，通道关闭再往下执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
    }

    @Override
    public void stop() {
        try {
            channel.close();
        } catch (Exception e) {
            throw new RuntimeException("客户端关闭出错",e);
        }
    }

    @Override
    public void sendRequest(RpcRequest rpcRequest) {

    }
}
