package com.cqp.cqprpc.service;


import com.cqp.cqprpc.annotation.RpcService;

@RpcService
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        //int i = 1 / 0;
        return "你好, " + name;
    }
}