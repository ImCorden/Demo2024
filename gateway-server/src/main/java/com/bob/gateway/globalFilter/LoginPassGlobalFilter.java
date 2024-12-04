package com.bob.gateway.globalFilter;


import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjUtil;
import com.bob.commontools.pojo.constants.BizConstants;
import com.bob.commontools.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

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

    private final RedisUtil redisUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // ServerHttpRequest request = exchange.getRequest();
        // HttpHeaders headers = request.getHeaders();
        // RequestPath path = request.getPath();
        // MultiValueMap<String, HttpCookie> cookies = request.getCookies();
        // InetSocketAddress remoteAddress = request.getRemoteAddress();
        // log.info("######## 通过请求 start ########");
        // log.info("remoteAddress={},path={}", remoteAddress, path);
        // log.info("header={},cookies={}", headers, cookies);
        // 如果已经登录续签时间，并取出student ID
        Object loginId = StpUtil.getLoginIdDefaultNull();
        if (ObjUtil.isNotEmpty(loginId)) {
            // 解析学生Id放入Headers
            String studentId = loginId.toString();
            ServerHttpRequest.Builder builder = exchange.getRequest().mutate();
            builder.header(BizConstants.HEADER_STUDENT_ID_KEY, studentId);
            exchange.mutate().request(builder.build()).build();
            // log.info("######## header putted {} : {}   ########", BizConstants.HEADER_STUDENT_ID_KEY, studentId);
            // 续签token时间
            // StpUtil.renewTimeout(RedisConstants.REDIS_SA_TOKEN_LOGIN_RENEW_TIME);
            // 续签Redis 中 Role信息
            // redisUtil.expire(RedisConstants.REDIS_USER_ROLES_LOGIN_KEY_PREFIX + studentId,
            //         RedisConstants.REDIS_USER_ROLES_LOGIN_KEY_RENEW_TIME, TimeUnit.MILLISECONDS);

        }
        // log.info("######## 通过请求 end   ########");
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
