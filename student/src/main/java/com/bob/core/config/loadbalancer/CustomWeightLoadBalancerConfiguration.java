package com.bob.core.config.loadbalancer;


import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @ClassName : CustomWeightLoadBalancerConfiguration
 * @Description : TODO
 * @Author : Bob
 * @Date : 2024/11/9 17:02
 * @Version : 1.0
 **/
// @Configuration
public class CustomWeightLoadBalancerConfiguration {

    @Bean
    ReactorLoadBalancer<ServiceInstance> weightLoadBalancer(
            Environment environment,
            LoadBalancerClientFactory loadBalancerClientFactory
    ) {
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);

        // 返回自定义负载均衡方式
        return new WeightLoadBalancer(
                loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class),
                name
        );

    }
}