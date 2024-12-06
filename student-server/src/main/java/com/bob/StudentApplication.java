package com.bob;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableDiscoveryClient
@EnableFeignClients
@LoadBalancerClients({
        @LoadBalancerClient(name = "shopping-server",
                configuration = com.alibaba.cloud.nacos.loadbalancer.NacosLoadBalancerClientConfiguration.class),
        @LoadBalancerClient(name = "DemoForMore",
                configuration = com.alibaba.cloud.nacos.loadbalancer.NacosLoadBalancerClientConfiguration.class)
})
@MapperScan(basePackages = "com.bob.*.mapper")
@EnableTransactionManagement
@SpringBootApplication
public class StudentApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentApplication.class, args);
    }
}