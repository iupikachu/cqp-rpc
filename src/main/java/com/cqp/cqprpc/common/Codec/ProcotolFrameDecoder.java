package com.cqp.cqprpc.common.Codec;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName ProcotolFrameDecoder.java
 * @Description 协议定长帧拼接器
 *              在发送消息前，先约定用定长字节表示接下来数据的长度
 *              会将分开发送、接收的消息根据消息的长度进行自动拼接
 *              确保服务端收到的是一个完整的消息
 *              无法复用每一个 channel 对应一个自己的ProcotolFrameDecoder 所以不能加 @Sharable
 * @createTime 2021年11月12日 21:53:00
 */
public class ProcotolFrameDecoder extends LengthFieldBasedFrameDecoder {

    public ProcotolFrameDecoder() {
        this(1024, 12, 4, 0, 0);
    }

    public ProcotolFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }

}
