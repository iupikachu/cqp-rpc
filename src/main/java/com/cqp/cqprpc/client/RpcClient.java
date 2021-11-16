package com.cqp.cqprpc.client;

import com.cqp.cqprpc.client.handler.RpcResponseHandler;
import com.cqp.cqprpc.message.RpcRequest;
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
import io.netty.util.concurrent.DefaultPromise;
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
            RpcResponseHandler rpcResponseHandler = new RpcResponseHandler();
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
                    ch.pipeline().addLast(rpcResponseHandler);


                }
            });
            channel = bootstrap.connect(this.getIp_address(), this.getPort()).sync().channel();

            int sequenceId = 1;  // 后续修改为 id 生成器
            // 模拟测试发送一个 rpc-request
            channel.writeAndFlush(
                    new RpcRequest(
                            sequenceId,
                            "com.cqp.cqprpc.service.HelloService",
                            "sayHello",
                                        String.class,
                                        new Class[]{String.class},
                                        new Object[]{"cqp"})
            ).addListener(promise ->{ // 异步检测 channel 是否发送成功
                if(!promise.isSuccess()){
                    Throwable cause = promise.cause();
                    log.error("error",cause);
                }else{
                    log.info("RpcRequest 发送成功!");
                }
            });

            //3.准备一个空 Promise 对象, 来接收结果                   指定 promise 对象，异步接收结果的线程
            DefaultPromise<Object> promise = new DefaultPromise<Object>(channel.eventLoop());
            RpcResponseHandler.PROMISES.put(sequenceId,promise);
            //4.等待 promise 结果，进入等待状态，promise有结果了，线程再进入就绪状态，等待cpu调度
            promise.await();
            if(promise.isSuccess()){
                // 调用正常
                System.out.println("promise.getNow(): "+promise.getNow());
            }else{
                // 调用失败
                throw new RuntimeException(promise.cause());
            }

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
            System.out.println("通道关闭了");
        } catch (Exception e) {
            //throw new RuntimeException("客户端关闭出错",e);
            e.printStackTrace();
        }
    }

    @Override
    public void sendRequest(RpcRequest rpcRequest) {

    }
}
