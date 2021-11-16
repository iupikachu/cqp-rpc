package com.cqp.cqprpc.client.handler;

import com.cqp.cqprpc.message.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.Promise;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName RpcResponseHandler.java
 * @Description 接收服务器回传的方法调用结果
 * @createTime 2021年11月16日 09:30:00
 */
public class RpcResponseHandler extends SimpleChannelInboundHandler<RpcResponse> {
    //sequenceId  用来接收异步调用结果的 Promise 对象  由于rpc调用的结果会有很多种，所以用<Object>
    public static final Map<Integer, Promise<Object>> PROMISES = new ConcurrentHashMap<>();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse msg) throws Exception  {
        // 拿到 promise  返回value值，并且将其移除
        Promise<Object> promise = PROMISES.remove(msg.getSequenceId());
        if(promise != null) {
            Object returnValue = msg.getReturnValue();
            Exception exception = msg.getException();
            if (exception != null) {
                promise.setFailure(exception);
            } else {
                promise.setSuccess(returnValue);
            }
        }
    }
}
