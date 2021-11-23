package com.cqp.cqprpc.server.register;

import com.cqp.cqprpc.annotation.RpcInjectService;
import com.cqp.cqprpc.annotation.RpcService;
import com.cqp.cqprpc.client.RpcClientProxy;
import com.cqp.cqprpc.server.RpcServer;
import com.cqp.cqprpc.service.HelloService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
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

    @Resource
    RpcServer rpcServer;

    @Resource
    RpcClientProxy rpcClientProxy;



    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
       if(Objects.isNull(event.getApplicationContext().getParent())){
           ApplicationContext context = event.getApplicationContext();

           // 开启服务
           startServer(context);

           // 注入服务
           //injectService(context);

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
                rpcServer.start();
            }
        }
    }

    public void injectService(ApplicationContext context){
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            Class<?> clazz = context.getType(name);
            if(Objects.isNull(clazz)){ continue;}
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                RpcInjectService rpcInjectService = field.getAnnotation(RpcInjectService.class);
                if(Objects.isNull(rpcInjectService)){ continue;}

                Class<?> fieldClass = field.getType();  // 拿到 com.service.userService
                Object bean = context.getBean(name); // 拿到 IndexController
                field.setAccessible(true);

                try {
                    Object proxy = rpcClientProxy.getProxy(fieldClass);
                    field.set(bean,proxy);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
