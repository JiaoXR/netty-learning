package com.jaxer.chat.session;

import io.netty.util.AttributeKey;

/**
 * Channel的属性
 *
 * @author jaxer
 * @date 2020/6/30 2:03 PM
 */
public class Attributes {
    public static final AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
