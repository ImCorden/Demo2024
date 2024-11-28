package com.bob.gateway.globalFilter;


import cn.dev33.satoken.stp.StpUtil;
import com.bob.commontools.pojo.BusinessConstants;
import com.bob.commontools.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName : LoginFilter
 * @Description : TODO
 * @Author : Bob
 * @Date : 2024/11/13 13:57
 * @Version : 1.0
 **/
// @Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoginPassGlobalFilter implements GlobalFilter, Ordered {

    private final RedisUtil redisUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        RequestPath path = request.getPath();
        MultiValueMap<String, HttpCookie> cookies = request.getCookies();
        InetSocketAddress remoteAddress = request.getRemoteAddress();
        log.info("######## 通过请求 start ########");
        log.info("remoteAddress={},path={}", remoteAddress, path);
        log.info("header={},cookies={}", headers, cookies);
        // 如果已经登录续签时间，并取出student ID
        if (StpUtil.isLogin()) {
            // 解析学生Id放入Headers
            String studentId = String.valueOf(StpUtil.getLoginId());
            ServerHttpRequest.Builder builder = exchange.getRequest().mutate();
            builder.header(BusinessConstants.HEADER_STUDENT_ID_KEY, studentId);
            exchange.mutate().request(builder.build()).build();
            log.info("######## header putted {} : {}   ########", BusinessConstants.HEADER_STUDENT_ID_KEY, studentId);
            // 续签token时间
            StpUtil.renewTimeout(BusinessConstants.REDIS_SA_TOKEN_LOGIN_RENEW_TIME);
            // 续签Redis 中 Role信息
            redisUtil.expire(BusinessConstants.REDIS_USER_ROLES_LOGIN_KEY_PREFIX + studentId,
                    BusinessConstants.REDIS_USER_ROLES_LOGIN_KEY_RENEW_TIME, TimeUnit.MILLISECONDS);

        }
        log.info("######## 通过请求 end   ########");
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
