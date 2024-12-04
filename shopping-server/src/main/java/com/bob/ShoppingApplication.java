package com.bob;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableFeignClients
@EnableTransactionManagement
@EnableDiscoveryClient
@LoadBalancerClients({
        @LoadBalancerClient(name = "student-server",
                configuration = com.alibaba.cloud.nacos.loadbalancer.NacosLoadBalancerClientConfiguration.class),
        @LoadBalancerClient(name = "DemoForMore",
                configuration = com.alibaba.cloud.nacos.loadbalancer.NacosLoadBalancerClientConfiguration.class)
})
@MapperScan("com.bob.*.mapper")
@SpringBootApplication
public class ShoppingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShoppingApplication.class, args);
    }

}
