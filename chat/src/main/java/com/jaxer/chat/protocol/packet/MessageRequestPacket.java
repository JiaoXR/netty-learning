package com.jaxer.chat.protocol.packet;

import com.jaxer.chat.protocol.Command;
import com.jaxer.chat.protocol.Packet;
import lombok.Data;

/**
 * 消息请求
 *
 * @author jaxer
 * @date 2020/6/29 2:58 PM
 */
@Data
public class MessageRequestPacket extends Packet {
    // 收信人
    private String toUserId;

    // 消息内容
    private String content;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
