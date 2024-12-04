package com.bob.config.redisson;


import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName : RedissonConfig
 * @Description : TODO
 *
 *
 * @Author : Bob
 * @Date : 2024/11/28 21:37
 * @Version : 1.0
 **/
@Slf4j
@Configuration
public class RedissonConfig {

    @Autowired
    private RedissonProperties redissonProperties;

    @Bean
    public RedissonClient redissonClient() throws Exception{
        Config config = Config.fromYAML(redissonProperties.getConfig());
        log.info("-------------------Redisson 已启动-------------------");
        return Redisson.create(config);
    }
}
