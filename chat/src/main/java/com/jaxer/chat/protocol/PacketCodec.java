package com.jaxer.chat.protocol;

import com.jaxer.chat.protocol.packet.LoginRequestPacket;
import com.jaxer.chat.protocol.packet.LoginResponsePacket;
import com.jaxer.chat.protocol.packet.MessageRequestPacket;
import com.jaxer.chat.protocol.packet.MessageResponsePacket;
import com.jaxer.chat.serialize.Serializer;
import com.jaxer.chat.serialize.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author jaxer
 * @date 2020/6/30 9:48 AM
 */
public class PacketCodec {
    public static final PacketCodec INSTANCE = new PacketCodec();
    public static final int MAGIC_NUMBER = 0x12345678;

    private final Map<Byte, Class<? extends Packet>> packetTypeMap = new HashMap<>();
    private final Map<Byte, Serializer> serializeMap = new HashMap<>();

    private PacketCodec() {
        // 包类型
        packetTypeMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(Command.MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(Command.MESSAGE_RESPONSE, MessageResponsePacket.class);

        // 序列化器
        Serializer serializer = new JSONSerializer();
        serializeMap.put(serializer.getSerializerAlgorithm(), serializer);
    }

    /**
     * 编码
     *
     * @param msg 要编码的消息
     * @param out 输出ByteBuf
     */
    public void encode(Packet msg, ByteBuf out) {
        byte[] data = Serializer.DEFAULT.serialize(msg);

        out.writeInt(MAGIC_NUMBER); // 魔数
        out.writeByte(msg.getVersion()); // 版本号
        out.writeByte(Serializer.DEFAULT.getSerializerAlgorithm()); // 序列化算法
        out.writeByte(msg.getCommand()); // Packet类型
        out.writeInt(data.length); // 数据长度
        out.writeBytes(data); // 真正的数据
    }

    /**
     * 解码
     */
    public Packet decode(ByteBuf in) {
        in.skipBytes(4); // 跳过魔数
        in.skipBytes(1); // 跳过版本号

        byte serializerAlgorithm = in.readByte(); // 序列化算法
        byte packetCommand = in.readByte(); // Packet类型

        int length = in.readInt(); // 数据包长度
        byte[] bytes = new byte[length];
        in.readBytes(bytes); // 真正的数据

        // 反序列化
        Serializer serializer = getSerializer(serializerAlgorithm);
        Class<? extends Packet> packetType = getPacketType(packetCommand);
        if (Objects.nonNull(serializer) && Objects.nonNull(packetType)) {
            return serializer.deserialize(packetType, bytes);
        }

        return null;
    }

    /**
     * 获取序列化器
     */
    private Serializer getSerializer(Byte serializerAlgorithm) {
        return serializeMap.get(serializerAlgorithm);
    }

    /**
     * 获取Packet类型
     */
    private Class<? extends Packet> getPacketType(Byte packetCommand) {
        return packetTypeMap.get(packetCommand);
    }
}
