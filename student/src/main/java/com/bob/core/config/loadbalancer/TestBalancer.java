package com.bob.core.config.loadbalancer;


import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import reactor.core.publisher.Mono;

/**
 * @ClassName : TestBalancer
 * @Description : TODO
 *
 *
 * @Author : Bob
 * @Date : 2024/11/9 17:19
 * @Version : 1.0
 **/
public class TestBalancer implements ReactorServiceInstanceLoadBalancer {

    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        return null;
    }
}
