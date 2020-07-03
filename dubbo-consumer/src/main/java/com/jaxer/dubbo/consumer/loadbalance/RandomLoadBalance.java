package com.jaxer.dubbo.consumer.loadbalance;

import java.util.List;
import java.util.Random;

/**
 * 负载均衡策略-随机算法
 * Created on 2020/7/3 23:33
 *
 * @author jaxer
 */
public class RandomLoadBalance implements LoadBalance {
    @Override
    public String choose(List<String> serviceList) {
        return serviceList.get(new Random().nextInt(serviceList.size()));
    }
}
