package com.cqp.cqprpc.server.register;

import com.cqp.cqprpc.annotation.RpcService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName DefaultRpcProcessor.java
 * @Description 处理服务注册和注入
 * @createTime 2021年11月18日 10:11:00
 */


public class DefaultRpcProcessor implements ApplicationListener<ContextRefreshedEvent> {

    @Resource
    ServiceRegister serviceRegister;



    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
       if(Objects.isNull(event.getApplicationContext().getParent())){
           ApplicationContext context = event.getApplicationContext();
           startServer(context);
       }
    }

    public void startServer(ApplicationContext context)  {
        Map<String, Object> beans = context.getBeansWithAnnotation(RpcService.class);
        if(beans.size() != 0){
            boolean startRpcServerFlag = true;
            for (Object obj : beans.values()) {
                try {
                    Class<?> clazz = obj.getClass();
                    Class<?>[] interfaces = obj.getClass().getInterfaces();
                    ServiceObject so;
                    if(interfaces.length != 1){  // 不止一个接口，看注解的值有没有说明是哪个接口
                        RpcService rpcService = clazz.getAnnotation(RpcService.class);
                        String value = rpcService.value();
                        if(value.equals("")){
                            startRpcServerFlag = false;
                            throw new UnsupportedOperationException("The exposed interface is not specific with '" + obj.getClass().getName() + "'");
                        }
                        so = new ServiceObject(value,Class.forName(value),obj);
                    }else {
                        Class<?> superClass = interfaces[0];
                        so = new ServiceObject(superClass.getName(),superClass.getClass(),obj);
                    }
                    // 把服务对象注册到 ZooKeeper 上去
                    serviceRegister.register(so);

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            if(startRpcServerFlag){
                // 开启rpcServer服务端
            }

        }
    }

}
