package com.bob.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@LoadBalancerClients({
        @LoadBalancerClient(name = "shopping-server",
                configuration = com.alibaba.cloud.nacos.loadbalancer.NacosLoadBalancerClientConfiguration.class),
        @LoadBalancerClient(name = "student-server",
                configuration = com.alibaba.cloud.nacos.loadbalancer.NacosLoadBalancerClientConfiguration.class)
})
@ComponentScan("com.bob")
@EnableConfigurationProperties
public class GatewayServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServerApplication.class, args);
    }

}
