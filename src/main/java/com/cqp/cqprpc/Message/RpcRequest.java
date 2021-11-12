package com.cqp.cqprpc.Message;

import java.util.Arrays;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName RpcRequest.java
 * @Description rpc请求
 * @createTime 2021年11月11日 20:03:00
 */
public class RpcRequest extends Message{

    @Override
    public int getMessageType() {
        return Message.RPC_MESSAGE_TYPE_REQUEST;
    }

    /**
     * 调用方法的接口名
     */
    private String intefaceName;

    /**
     * 调用方法名
     */
    private String methodName;

    /**
     * 方法返回值类型
     */
    private Class<?> returnType;

    /**
     * 方法参数类型数组
     */
    private Class[] parameterTypes;

    /**
     * 方法参数列表
     */
    private Object[] parameterValues;

    // 没有无参构造器，jackson 反序列化会报错
    public RpcRequest() {
    }

    public RpcRequest(String intefaceName, String methodName, Class<?> returnType, Class[] parameterTypes, Object[] parameterValues) {
        this.intefaceName = intefaceName;
        this.methodName = methodName;
        this.returnType = returnType;
        this.parameterTypes = parameterTypes;
        this.parameterValues = parameterValues;
    }

    public String getIntefaceName() {
        return intefaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public Class[] getParameterTypes() {
        return parameterTypes;
    }

    public Object[] getParameterValues() {
        return parameterValues;
    }

    @Override
    public String toString() {
        return "RpcRequest{" +
                "intefaceName='" + intefaceName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", returnType=" + returnType +
                ", parameterTypes=" + Arrays.toString(parameterTypes) +
                ", parameterValues=" + Arrays.toString(parameterValues) +
                '}';
    }
}
