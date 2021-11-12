package com.cqp.cqprpc.common.Codec;

import com.cqp.cqprpc.Message.RpcRequest;
import com.cqp.cqprpc.common.protocol.User;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.logging.LoggingHandler;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName TestMessageCodecSharable.java
 * @Description TODO
 * @createTime 2021年11月12日 14:11:00
 */
@SpringBootTest
public class TestMessageCodecSharable {
    @Test
    public void Test() throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(
                new LoggingHandler(),
                new MessageCodecSharable()
        );
        // 测试 encode 方法
        RpcRequest rpcRequest = new RpcRequest("UserService","getUser", User.class,new Class[]{Integer.class,String.class},new Object[]{1,"a"});
        channel.writeOutbound(rpcRequest);

        // 测试 decode 方法
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        List<Object> objects = new ArrayList<>();


       // new MessageCodecSharable().encode(defaultChannelHandlerContext, rpcRequest, objects);
        channel.writeInbound(buffer);
    }
}
