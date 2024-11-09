package com.bob.core.config;


import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName : RestTempLateConfig
 * @Description : TODO
 * @Author : Bob
 * @Date : 2024/11/9 16:52
 * @Version : 1.0
 **/
@Configuration
public class RestTempLateConfig {

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
