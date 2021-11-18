package com.cqp.cqprpc.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName RpcService.java
 * @Description TODO
 * @createTime 2021年11月18日 09:46:00
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface RpcService {
    String value() default "";
}
