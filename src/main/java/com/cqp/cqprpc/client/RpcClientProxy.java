package com.cqp.cqprpc.client;

import com.cqp.cqprpc.client.handler.RpcResponseHandler;
import com.cqp.cqprpc.common.Codec.MessageCodecSharable;
import com.cqp.cqprpc.common.Codec.ProcotolFrameDecoder;
import com.cqp.cqprpc.common.SequenceIdGenerator;
import com.cqp.cqprpc.message.RpcRequest;
import com.cqp.cqprpc.message.RpcResponse;
import com.cqp.cqprpc.service.HelloService;
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

import java.lang.reflect.Proxy;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName RpcClientProxy.java
 * @Description 动态代理
 * @createTime 2021年11月14日 12:49:00
 */
@Slf4j
public class RpcClientProxy {


    private  String ip_address;
    private  int  port;

    public RpcClientProxy(String ip_address, int port) {
        this.ip_address = ip_address;
        this.port = port;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    private static Channel channel = null;
    private static final Object LOCK = new Object();

    // 单例模式获取 Channel
    public  Channel getChannel(){
        if(channel != null){
            return channel;
        }
        synchronized (LOCK){
          if(channel != null){
              return channel;
          }else {
              this.initChannel();
              return channel;
          }
        }
    }




    private  void initChannel() {
            NioEventLoopGroup group = new NioEventLoopGroup();
            LoggingHandler loggingHandler = new LoggingHandler(LogLevel.DEBUG);
            MessageCodecSharable messageCodec = new MessageCodecSharable();
            RpcResponseHandler rpcResponseHandler = new RpcResponseHandler();

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

        try {
            channel = bootstrap.connect(ip_address,port).sync().channel();
            channel.closeFuture().addListener(future ->{
                group.shutdownGracefully();
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public  <T> T getProxy(Class<T> serviceClass){
        ClassLoader classLoader = serviceClass.getClassLoader();
        Class[] interfaces = new Class[]{serviceClass};

        Object o = Proxy.newProxyInstance(classLoader, interfaces, ((proxy, method, args) -> {
                    int sequenceId = SequenceIdGenerator.nextId();
                    RpcRequest rpcRequest = new RpcRequest(
                            sequenceId,
                            serviceClass.getName(),
                            method.getName(),
                            method.getReturnType(),
                            method.getParameterTypes(),
                            args
                    );
                    getChannel().writeAndFlush(rpcRequest);
                    //准备一个空 Promise 对象, 来接收结果                   指定 promise 对象，异步接收结果的线程
                    DefaultPromise<Object> promise = new DefaultPromise<Object>(getChannel().eventLoop());
                    RpcResponseHandler.PROMISES.put(sequenceId, promise);

                    //4.等待 promise 结果，进入等待状态，promise有结果了，线程再进入就绪状态，等待cpu调度
                    promise.await();
                    if (promise.isSuccess()) {
                        // 调用正常
                        return promise.getNow();
                    } else {
                        // 调用失败
                        throw new RuntimeException(promise.cause());
                    }
                })
        );
        return (T) o;
    }

    public static void main(String[] args) {
        RpcClientProxy rpcClientProxy= new RpcClientProxy("localhost", 8080);
        HelloService proxy = rpcClientProxy.getProxy(HelloService.class);
        proxy.sayHello("cqp");
        proxy.sayHello("iu");
    }
}
