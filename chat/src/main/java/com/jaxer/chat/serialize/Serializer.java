package com.jaxer.chat.serialize;

import com.jaxer.chat.serialize.impl.JSONSerializer;

/**
 * 序列化
 *
 * @author jaxer
 * @date 2020/6/30 9:32 AM
 */
public interface Serializer {
    /**
     * 默认序列化方式
     */
    Serializer DEFAULT = new JSONSerializer();

    /**
     * 获取序列化算法
     */
    byte getSerializerAlgorithm();

    /**
     * Java对象序列化为二进制
     */
    byte[] serialize(Object obj);

    /**
     * 二进制转换成 java 对象
     */
    <T> T deserialize(Class<T> cls, byte[] bytes);
}
