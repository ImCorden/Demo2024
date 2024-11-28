package com.bob.config;


import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName : RestTempLateConfig
 * @Description : TODO
 * @Author : Bob
 * @Date : 2024/11/9 16:52
 * @Version : 1.0
 **/
@Configuration
public class RestTemplateConfig {

    @LoadBalanced
    @SentinelRestTemplate
    @Bean
    // public RestTemplate restTemplate() {
    //     return new RestTemplate();
    // }
    public RestTemplate restTemplate(ClientHttpRequestFactory requestFactory) {
        return new RestTemplate(requestFactory);
    }


    @Bean
    public ClientHttpRequestFactory httpRequestFactory() {
        return new HttpComponentsClientHttpRequestFactory(httpClient());
    }

    /**
     * @return
     */
    @Bean
    public HttpClient httpClient() {
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .build();

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);

        // 设置整个连接池最大连接数
        connectionManager.setMaxTotal(500);

        // MaxPerRoute路由是对maxTotal的细分,每个主机的并发，这里route指的是域名
        connectionManager.setDefaultMaxPerRoute(200);
        RequestConfig requestConfig = RequestConfig.custom()
                // 返回数据的超时时间
                // .setSocketTimeout(20000,)
                .setResponseTimeout(20000, TimeUnit.MILLISECONDS)
                // 连接上服务器的超时时间
                .setConnectTimeout(10000, TimeUnit.MICROSECONDS)

                // 从连接池中获取连接的超时时间
                .setConnectionRequestTimeout(1000, TimeUnit.MICROSECONDS)
                .build();

        return HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connectionManager)
                .build();
    }
}
