package com.cqp.cqprpc.common;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName SequenceIdGenerator.java
 * @Description 消息序列号生成器
 * @createTime 2021年11月16日 10:07:00
 */
public class SequenceIdGenerator {
    private static final AtomicInteger id = new AtomicInteger();

    public static int nextId(){ return id.incrementAndGet();}
}
