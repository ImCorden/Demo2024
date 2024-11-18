package com.bob.gateway.filter;


import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import com.bob.commontools.pojo.BusinessConstants;
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
import java.util.Optional;

/**
 * @ClassName : LoginFilter
 * @Description : TODO
 * @Author : Bob
 * @Date : 2024/11/13 13:57
 * @Version : 1.0
 **/
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoginPassGlobalFilter implements GlobalFilter, Ordered {

    // 配置中心动态刷新鉴权
    // private final SaTokenUrlConfig permissionUrlConfig;

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
            // 续签token时间
            StpUtil.renewTimeout(3000);
            // 解析学生Id放入Headers
            Object loginId = StpUtil.getLoginId();
            if (ObjectUtil.isNotNull(loginId)) {
                String studentId = String.valueOf(loginId);
                ServerHttpRequest.Builder builder = request.mutate();
                builder.header(BusinessConstants.HEADER_STUDENT_ID_KEY, studentId);
                exchange.mutate().request(builder.build()).build();
                log.info("######## header putted {} : {}   ########", BusinessConstants.HEADER_STUDENT_ID_KEY, studentId);
            }
        }
        log.info("######## 通过请求 end   ########");
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
