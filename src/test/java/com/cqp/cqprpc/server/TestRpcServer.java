package com.cqp.cqprpc.server;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName TestRpcServer.java
 * @Description TODO
 * @createTime 2021年11月12日 10:00:00
 */
@SpringBootTest
public class TestRpcServer {


    @Test
    public void TestRpcServer(){
        RpcServer rpcServer = new RpcServer(8080);
        rpcServer.start();

    }


}
