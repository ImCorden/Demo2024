package com.bob;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;


@EnableDiscoveryClient
@EnableFeignClients
@LoadBalancerClients({
        @LoadBalancerClient(name = "ShoppingServer",
                configuration = com.alibaba.cloud.nacos.loadbalancer.NacosLoadBalancerClientConfiguration.class),
        @LoadBalancerClient(name = "DemoForMore",
                configuration = com.alibaba.cloud.nacos.loadbalancer.NacosLoadBalancerClientConfiguration.class)
})
@MapperScan("com.bob.*.mapper")
@EnableTransactionManagement
@SpringBootApplication
public class StudentApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentApplication.class, args);
    }
}