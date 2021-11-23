package com.cqp.cqprpc.server.handler;

import com.cqp.cqprpc.message.RpcRequest;
import com.cqp.cqprpc.message.RpcResponse;
import com.cqp.cqprpc.server.register.ServiceObject;
import com.cqp.cqprpc.server.register.ServiceRegister;
import com.cqp.cqprpc.server.register.ZooKeeperRegister;
import com.cqp.cqprpc.util.ApplicationContextUtils;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName RpcRequestHandler.java
 * @Description 服务端接受 Rpc 请求
 * @createTime 2021年11月14日 11:03:00
 */
@Slf4j
public class RpcRequestHandler extends SimpleChannelInboundHandler<RpcRequest> {




    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest rpcRequest) throws Exception {
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setSequenceId(rpcRequest.getSequenceId());

        // 获取RpcRequest 里的接口信息，去 zookeeper 上获取该接口的实现类(服务发现)
        String intefaceName = rpcRequest.getIntefaceName();// com.cqp.cqprpc.service.HelloService 来获得  HelloServiceImpl 对象
        System.out.println("interfaceName:" + intefaceName);
        // 暂时模拟zookeeper的功能
        try {
            ServiceRegister serviceRegister = (ServiceRegister) ApplicationContextUtils.getBean("serviceRegister");
            ServiceObject serviceObject = serviceRegister.getServiceObject(intefaceName);
            Object obj = serviceObject.getObj(); // serviceImpl对象
            Class<?> clazz = obj.getClass(); // serviceImpl Class 对象
            System.out.println(obj.getClass().getName());
            Method method = clazz.getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
            Object returnObject = method.invoke(obj, rpcRequest.getParameterValues());
            rpcResponse.setReturnValue(returnObject);
            rpcResponse.setReturnClassType(rpcRequest.getReturnType());
//            if(intefaceName.equals("com.cqp.cqprpc.service.HelloService")){
//                String className = "com.cqp.cqprpc.service.HelloServiceImpl";
//                Class<?> clazz = Class.forName(className);
//                Object instance = clazz.newInstance();
//                Method method = clazz.getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
//                Object returnObject = method.invoke(instance, rpcRequest.getParameterValues());
//                rpcResponse.setReturnValue(returnObject);
//            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("远程调用出错!",e);
        }

        // 发送 RpcResponse
        ChannelFuture channelFuture = ctx.writeAndFlush(rpcResponse).addListener(future -> {
            if(!future.isSuccess()){
                log.info("发送 RpcResponse 出错");
                Throwable cause = future.cause();
            }else{
                log.info("发送 RpcResponse 成功");
            }
        });

    }
}
