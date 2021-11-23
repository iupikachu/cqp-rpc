package com.cqp.cqprpc.controller;

import com.cqp.cqprpc.annotation.RpcInjectService;
import com.cqp.cqprpc.service.HelloService;
import org.springframework.stereotype.Component;


/**
 * @author cqp
 * @version 1.0.0
 * @ClassName IndexController.java
 * @Description TODO
 * @createTime 2021年11月18日 21:47:00
 */

@Component
public class IndexController {



    @RpcInjectService
    private HelloService helloService;
    /**
     * 获取用户信息
     * http://localhost:8080/index/getUser?id=1
     *
     * @param id 用户id
     * @return 用户信息
     */

}
