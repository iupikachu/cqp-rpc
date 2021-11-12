package com.cqp.cqprpc.client;

import com.cqp.cqprpc.Message.RpcRequest;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName RpcClient.java
 * @Description rpc客户端
 * @createTime 2021年11月12日 14:48:00
 */
public class RpcClient extends Client{
    public RpcClient(String ip_address, int port) {
        super(ip_address, port);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void sendRequest(RpcRequest rpcRequest) {

    }
}
