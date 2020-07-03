package com.jaxer.dubbo.consumer.loadbalance;

import java.util.List;

/**
 * 负载均衡
 * Created on 2020/7/3 23:32
 *
 * @author jaxer
 */
public interface LoadBalance {
    /**
     * 从服务列表中选择一个
     *
     * @param serviceList 服务列表
     * @return 被选中的服务
     */
    String choose(List<String> serviceList);
}
