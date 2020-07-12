package com.jaxer.chat.protocol.packet;

import com.jaxer.chat.protocol.Command;
import com.jaxer.chat.protocol.Packet;
import lombok.Data;

/**
 * 登录请求
 *
 * @author jaxer
 * @date 2020/6/29 2:30 PM
 */
@Data
public class LoginRequestPacket extends Packet {
    private String username;

    private String pwd = "123456";

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
