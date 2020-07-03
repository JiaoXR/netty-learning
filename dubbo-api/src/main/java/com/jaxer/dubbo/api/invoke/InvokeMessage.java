package com.jaxer.dubbo.api.invoke;

import lombok.Data;

import java.io.Serializable;

/**
 * Created on 2020/6/27 12:45
 *
 * @author jaxer
 */
@Data
public class InvokeMessage implements Serializable {
    private static final long serialVersionUID = 2676036821663366438L;

    public String serviceName;

    public String methodName;

    public Class<?>[] parameterTypes;

    public Object[] parameterValues;
}
