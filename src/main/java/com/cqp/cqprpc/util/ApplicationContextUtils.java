package com.cqp.cqprpc.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName ApplicationContextUtils.java
 * @Description 让不在Spring容器中的类可以获取到容器里的Bean
 * @createTime 2021年11月20日 11:03:00
 */

@Component
public class ApplicationContextUtils implements ApplicationContextAware {
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static Object getBean(String beanName) {
        return context.getBean(beanName);
    }
}


