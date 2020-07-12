package com.jaxer.chat.protocol;

/**
 * 消息类型
 *
 * @author jaxer
 * @date 2020/6/29 2:53 PM
 */
public interface Command {
    Byte LOGIN_REQUEST = 1;

    Byte LOGIN_RESPONSE = 2;

    Byte MESSAGE_REQUEST = 3;

    Byte MESSAGE_RESPONSE = 4;
}
