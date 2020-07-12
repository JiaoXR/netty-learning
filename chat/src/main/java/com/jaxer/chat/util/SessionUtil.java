package com.jaxer.chat.util;

import com.jaxer.chat.session.Attributes;
import com.jaxer.chat.session.Session;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jaxer
 * @date 2020/6/30 10:37 AM
 */
public class SessionUtil {
    private static final Map<String, Channel> userIdChannelMap = new HashMap<>();

    public static void bindSession(Session session, Channel channel) {
        userIdChannelMap.put(session.getUserId(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            userIdChannelMap.remove(getSession(channel).getUserId());
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    /**
     * 用户是否已登录
     */
    public static boolean hasLogin(Channel channel) {
        return channel.hasAttr(Attributes.SESSION);
    }

    /**
     * 根据userId获取Channel
     */
    public static Channel getChannel(String userId) {
        return userIdChannelMap.get(userId);
    }

    public static Session getSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }
}
