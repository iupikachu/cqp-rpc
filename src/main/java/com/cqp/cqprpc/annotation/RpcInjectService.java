package com.cqp.cqprpc.annotation;

import java.lang.annotation.*;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName RpcInjectService.java
 * @Description 注入远程服务
 * @createTime 2021年11月18日 09:30:00
 */

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RpcInjectService {
}
