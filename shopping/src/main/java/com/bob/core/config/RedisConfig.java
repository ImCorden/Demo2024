package com.bob.core.config;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.TimeZone;

/**
 * @ClassName : RedisConfig
 * @Description : 详细见
 * <a href="https://docs.spring.io/spring-data/data-redis/docs/3.0.1/reference/html/#redis:connectors">官方文档</a>
 * @Author : Bob
 * @Date : 2024/11/7 11:26
 * @Version : 1.0
 **/
@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.database}")
    private int database;
    @Value("${spring.data.redis.host}")
    private String host;
    @Value("${spring.data.redis.port}")
    private int port;
    @Value("${spring.data.redis.password}")
    private String password;
    @Value("${spring.data.redis.timeout}")
    private int timeout;
    @Value("${spring.data.redis.jedis.pool.max-active}")
    private int maxActive;
    @Value("${spring.data.redis.jedis.pool.max-idle}")
    private int maxIdle;
    @Value("${spring.data.redis.jedis.pool.min-idle}")
    private int minIdle;
    @Value("${spring.data.redis.jedis.pool.max-wait}")
    private long maxWaitMillis;
    @Value("${spring.data.redis.ssl}")
    private boolean ssl;

    /**
     * Jedis 配置
     * @return
     */
    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration(host, port);
        redisConfig.setPassword(RedisPassword.of(password));
        redisConfig.setDatabase(database);

        GenericObjectPoolConfig<Object> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMinIdle(minIdle);
        poolConfig.setMaxWaitMillis(maxWaitMillis);
        // 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
        poolConfig.setTestOnBorrow(true);
        // 是否启用pool的jmx管理功能, 默认true
        poolConfig.setJmxEnabled(true);

        JedisClientConfiguration jedisClientConfiguration;
        if (ssl) {
            jedisClientConfiguration = JedisClientConfiguration.builder()
                    .usePooling()
                    .poolConfig(poolConfig)
                    .and()
                    .readTimeout(Duration.ofMillis(timeout))
                    .useSsl()
                    .build();
        } else {
            jedisClientConfiguration = JedisClientConfiguration.builder()
                    .usePooling().poolConfig(poolConfig).build();
        }
        return new JedisConnectionFactory(redisConfig, jedisClientConfiguration);
    }

    /**
     * RedisTemplate 配置
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        RedisSerializer<Object> serializer = getRedisSerializer();
        // 设置序列化器
        redisTemplate.setDefaultSerializer(serializer);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(serializer);
        // 开启事务
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * Redis序列化方式
     *
     * @return
     */
    private RedisSerializer<Object> getRedisSerializer() {
        ObjectMapper om = new ObjectMapper();
        om.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        // 序列化规则
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(
                LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.WRAPPER_ARRAY);
        // 解决LocalDateTime的序列化错误
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        om.registerModule(new JavaTimeModule());
        return new Jackson2JsonRedisSerializer<>(om,Object.class);
    }
}

