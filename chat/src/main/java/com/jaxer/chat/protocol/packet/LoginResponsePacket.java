package com.jaxer.chat.protocol.packet;

import com.jaxer.chat.protocol.Command;
import com.jaxer.chat.protocol.Packet;
import lombok.Data;

/**
 * 登录响应
 *
 * @author jaxer
 * @date 2020/6/29 2:57 PM
 */
@Data
public class LoginResponsePacket extends Packet {
    private String userId;

    private String username;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
