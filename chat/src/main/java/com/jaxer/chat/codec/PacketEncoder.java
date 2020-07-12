package com.jaxer.chat.codec;

import com.jaxer.chat.protocol.Packet;
import com.jaxer.chat.protocol.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Packet对象编码
 *
 * @author jaxer
 * @date 2020/6/30 9:38 AM
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
        PacketCodec.INSTANCE.encode(msg, out);
    }
}
