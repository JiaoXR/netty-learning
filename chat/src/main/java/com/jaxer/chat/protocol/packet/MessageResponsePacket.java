package com.jaxer.chat.protocol.packet;

import com.jaxer.chat.protocol.Command;
import com.jaxer.chat.protocol.Packet;
import lombok.Data;

/**
 * 消息响应
 *
 * @author jaxer
 * @date 2020/6/29 2:58 PM
 */
@Data
public class MessageResponsePacket extends Packet {
    private String fromUserId;

    private String fromUsername;

    private String content;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
