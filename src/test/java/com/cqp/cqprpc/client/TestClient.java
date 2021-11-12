package com.cqp.cqprpc.client;

import com.cqp.cqprpc.server.RpcServer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName TestClient.java
 * @Description 测试客户端发送 RpcRequest
 * @createTime 2021年11月12日 21:33:00
 */

@SpringBootTest
public class TestClient {
//    @BeforeAll
//    public static void startServer(){
//        RpcServer rpcServer = new RpcServer(8080);
//        rpcServer.start();
//    }

    @Test
    public void sendRpcRequest(){
        RpcClient rpcClient = new RpcClient("127.0.0.1", 8080);
        rpcClient.start();
    }
}
