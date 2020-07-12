package com.jaxer.chat.serialize.impl;

import com.alibaba.fastjson.JSON;
import com.jaxer.chat.serialize.Serializer;
import com.jaxer.chat.serialize.SerializerAlgorithm;

/**
 * JSON序列化
 *
 * @author jaxer
 * @date 2020/6/30 9:34 AM
 */
public class JSONSerializer implements Serializer {

    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object obj) {
        return JSON.toJSONBytes(obj);
    }

    @Override
    public <T> T deserialize(Class<T> cls, byte[] bytes) {
        return JSON.parseObject(bytes, cls);
    }
}
