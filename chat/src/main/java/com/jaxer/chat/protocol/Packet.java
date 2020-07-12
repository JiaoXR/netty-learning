package com.jaxer.chat.protocol;

import lombok.Data;

import java.io.Serializable;

/**
 * 消息包
 *
 * @author jaxer
 * @date 2020/6/29 2:55 PM
 */
@Data
public abstract class Packet implements Serializable {
    private static final long serialVersionUID = -7967997897948132879L;

    // 版本号
    private Byte version = 1;

    // 消息类型
    public abstract Byte getCommand();
}
