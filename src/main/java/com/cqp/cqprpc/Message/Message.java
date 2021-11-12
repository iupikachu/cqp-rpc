package com.cqp.cqprpc.Message;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName Message.java
 * @Description 消息类 （可替换为 enum ）
 * @createTime 2021年11月11日 19:51:00
 */
public abstract class Message implements Serializable {

    // rpc消息类型 用于在自定义协议中标识出消息类型
    public static final int RPC_MESSAGE_TYPE_REQUEST = 1;
    public static final int RPC_MESSAGE_TYPE_RESPONSE = 2;

    // 异步通信 序列号
    private int sequenceId;

    // 消息类型
    private int messageType;

    /**
     * 根据消息类型，得到消息类
     * @param messageType
     * @return
     */
    public static Class<? extends Message> getMessageClassByType(int messageType){
        return messageClasses.get(messageType);
    }

    public abstract int getMessageType();


    // 记录 rpc消息和标识id的对应关系
    private static final Map<Integer, Class<? extends Message>> messageClasses = new HashMap<>();

    static {
        messageClasses.put(RPC_MESSAGE_TYPE_REQUEST, RpcRequest.class);
        messageClasses.put(RPC_MESSAGE_TYPE_RESPONSE,RpcResponse.class);
    }

    public int getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(int sequenceId) {
        this.sequenceId = sequenceId;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }
}
