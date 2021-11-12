package com.cqp.cqprpc.common.protocol;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName Serialization.java
 * @Description 序列化、反序列化
 * @createTime 2021年11月11日 20:29:00
 */
public interface Serialization {

    // 序列化
    <T> byte[] serialize(T object);

    // 反序列化
    <T> T deserialize(Class<T> clazz, byte[] bytes);


}
