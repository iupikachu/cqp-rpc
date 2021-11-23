package com.cqp.cqprpc.message;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName RpcResponse.java
 * @Description rpc 回应消息
 * @createTime 2021年11月11日 20:11:00
 */
public class RpcResponse extends Message{

    @Override
    public int getMessageType() {
        return Message.RPC_MESSAGE_TYPE_RESPONSE;
    }

    // 方法调用返回值
    private Object returnValue;

    // 方法调用返回值类型
    private Class<?> returnClassType;

    // 方法调用出错，返回异常对象
    private Exception exception;

    // 没有无参构造器，jackson 反序列化会报错
    public RpcResponse() {
    }

    public RpcResponse(Object returnValue, Exception exception, Class<?> returnClassType) {
        this.returnValue = returnValue;
        this.exception = exception;
        this.returnClassType = returnClassType;
    }

    public Object getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public Class<?> getReturnClassType() {
        return returnClassType;
    }

    public void setReturnClassType(Class<?> returnClassType) {
        this.returnClassType = returnClassType;
    }

    @Override
    public String toString() {
        return "RpcResponse{" +
                "returnValue=" + returnValue +
                ", returnClassType=" + returnClassType +
                ", exception=" + exception +
                '}';
    }
}
