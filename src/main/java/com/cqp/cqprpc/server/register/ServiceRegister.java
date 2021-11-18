package com.cqp.cqprpc.server.register;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName ServiceRegister.java
 * @Description 服务注册器
 * @createTime 2021年11月18日 14:18:00
 */
public interface ServiceRegister {
    void register(ServiceObject serviceObject);

    ServiceObject getServiceObject(String name);
}
