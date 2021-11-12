package com.cqp.cqprpc.common.protocol;

import com.cqp.cqprpc.Message.RpcRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName SerializeTest.java
 * @Description TODO
 * @createTime 2021年11月11日 21:19:00
 */
@SpringBootTest
public class SerializeTest {
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testJdkSerialize(){
        byte[] bytes = Serializer.jdk.serialize(new User("cqp", 23));
        System.out.println(bytes);
        User user = Serializer.jdk.deserialize(User.class, bytes);
        System.out.println(user);
    }

    @Test
    public void testJsonSerialize(){
        byte[] bytes = Serializer.json.serialize(new User("cqp", 23));
        System.out.println(bytes);
        User user = Serializer.json.deserialize(User.class, bytes);
        System.out.println(user);
    }

    @Test
    public void testjackson() throws IOException {


        byte[] bytes = objectMapper.writeValueAsBytes(new User("cqp", 24));
        System.out.println(new String(bytes));
        User user = objectMapper.readValue(bytes, User.class);
        System.out.println(user);

    }


}
