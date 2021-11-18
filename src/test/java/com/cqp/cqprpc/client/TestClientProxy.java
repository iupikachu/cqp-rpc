package com.cqp.cqprpc.client;

import com.cqp.cqprpc.service.HelloService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName TestClientProxy.java
 * @Description TODO
 * @createTime 2021年11月16日 21:19:00
 */
@SpringBootTest
public class TestClientProxy {
    @Test
    public void testClientProxy(){
        RpcClientProxy rpcClientProxy= new RpcClientProxy("localhost", 8080);
        HelloService proxy = rpcClientProxy.getProxy(HelloService.class);
        proxy.sayHello("cqp");
        proxy.sayHello("iu");
    }
}
