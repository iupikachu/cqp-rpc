package com.cqp.cqprpc.config;

import com.cqp.cqprpc.server.register.DefaultRpcProcessor;
import com.cqp.cqprpc.server.register.ServiceRegister;
import com.cqp.cqprpc.server.register.ZooKeeperRegister;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName AutoConfiguration.java
 * @Description 自动配置类
 * @createTime 2021年11月18日 10:46:00
 */

@Configuration
public class AutoConfiguration {
    @Bean
   public DefaultRpcProcessor defaultRpcProcessor(){ return new DefaultRpcProcessor();}

    @Bean
    public ServiceRegister serviceRegister() throws UnknownHostException {
        String host = InetAddress.getLocalHost().getHostAddress();
       return new ZooKeeperRegister("47.110.154.185:2181","host","18000");
    }
}
