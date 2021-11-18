package com.cqp.cqprpc.server.register;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName DefaultServiceRegister.java
 * @Description 默认服务注册器 注册到本地内存中
 * @createTime 2021年11月18日 14:20:00
 */
public class DefaultServiceRegister implements ServiceRegister{
    private Map<String,ServiceObject> serviceMap = new HashMap<>();

    @Override
    public void register(ServiceObject serviceObject) {
        if(serviceObject == null){
            throw new IllegalArgumentException("Parameter cannot be empty.");
        }
        this.serviceMap.put(serviceObject.getName(),serviceObject);
    }

    @Override
    public ServiceObject getServiceObject(String name) {
        return this.serviceMap.get(name);
    }
}
