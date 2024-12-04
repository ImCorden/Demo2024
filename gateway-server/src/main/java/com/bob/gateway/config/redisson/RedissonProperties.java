package com.bob.gateway.config.redisson;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName : RedissonConfig
 * @Description : Redisson配置
 * @Author : Bob
 * @Date : 2024/11/28 17:25
 * @Version : 1.0
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.data.redis.redisson")
public class RedissonProperties {

    private String config;

    private String file;

}
