package com.cqp.cqprpc.Message;

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

    // 方法调用出错，返回异常对象
    private Exception exception;

    // 没有无参构造器，jackson 反序列化会报错
    public RpcResponse() {
    }

    public RpcResponse(Object returnValue, Exception exception) {
        this.returnValue = returnValue;
        this.exception = exception;
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

    @Override
    public String toString() {
        return "RpcResponse{" +
                "returnValue=" + returnValue +
                ", exception=" + exception +
                '}';
    }
}
