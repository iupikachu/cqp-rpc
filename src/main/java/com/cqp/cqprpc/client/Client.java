package com.cqp.cqprpc.client;

import com.cqp.cqprpc.Message.RpcRequest;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName Client.java
 * @Description 客户端抽象类
 * @createTime 2021年11月12日 14:39:00
 */
public abstract class Client {
    private String ip_address;
    private int port;

    // 启动客户端
    public abstract void start();

    // 停止客户端
    public abstract void stop();

    // 发送rpc 请求消息
    public abstract void sendRequest(RpcRequest rpcRequest);

    public Client(String ip_address, int port) {
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
}
