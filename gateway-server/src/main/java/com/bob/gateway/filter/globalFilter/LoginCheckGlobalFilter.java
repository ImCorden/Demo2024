package com.bob.gateway.filter.globalFilter;


import cn.dev33.satoken.reactor.context.SaReactorSyncHolder;
import cn.dev33.satoken.stp.StpUtil;


import com.bob.commontools.pojo.BusinessConstants;
import com.bob.commontools.utils.GsonHelper;
import com.bob.commontools.utils.RedisUtil;
import com.bob.gateway.config.saToken.SaTokenUrlRule;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @ClassName : LoginCheckGlobalFilter
 * @Description : 自定义登录校验全局过滤器
 * @Author : Bob
 * @Date : 2024/11/13 13:57
 * @Version : 1.0
 **/
// @Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoginCheckGlobalFilter implements GlobalFilter, Ordered {
    private final RedisUtil redisUtil;
    private final SaTokenUrlRule saTokenUrlRule;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // long start = System.currentTimeMillis();
        // 不需要权限验证，直接过
        String path = exchange.getRequest().getPath().toString();
        List<String> excludes = saTokenUrlRule.getExcludes();
        if (excludes.contains(path)) {
            return chain.filter(exchange);
        }
        // 需要权限验证，进行Sa-Token验证
        SaReactorSyncHolder.setContext(exchange);
        if (StpUtil.isLogin()) {
            String studentId = String.valueOf(StpUtil.getLoginId());

            List<String> userPer = this.getPermission(studentId);

            long count = saTokenUrlRule.getPermissions().stream()
                    .filter(permission -> path.startsWith(permission.getUri()))
                    .filter(permission -> userPer.contains(permission.getPermission()))
                    .count();
            if (count > 0) {
                ServerHttpRequest res = exchange.getRequest()
                        .mutate()
                        .header(BusinessConstants.HEADER_STUDENT_ID_KEY, studentId)
                        .build();
                ServerWebExchange newServerWebExchange = exchange.mutate().request(res).build();
                // long end = System.currentTimeMillis();
                // log.info("{}ms", end - start);
                return chain.filter(newServerWebExchange);
            }
        }
        return buildErrorResponse(exchange, "错误！！");
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }


    private List<String> getPermission(String loginId) {
        List<String> res = new ArrayList<>();
        //取出学生角色
        String jsonRole = redisUtil.get(BusinessConstants.REDIS_USER_ROLES_LOGIN_KEY_PREFIX + loginId);
        ArrayList<String> roles = GsonHelper.json2Object(jsonRole, new TypeToken<ArrayList<String>>() {}.getType());
        // 取出所有系统角色的权限
        Map<Object, Object> pers = redisUtil.hGetAll("permissions");
        pers.forEach((k, v) -> {
            if (roles.contains(k.toString())) {
                res.addAll(GsonHelper.json2Object(v.toString(), new TypeToken<List<String>>() {}.getType()));
            }
        });
        return res;
    }

    private Mono<Void> buildErrorResponse(ServerWebExchange exchange, String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.OK); // 设置响应码为200
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);

        String jsonResponse = String.format("{\"code\":500, \"msg\":\"%s\"}", message);
        byte[] bytes = jsonResponse.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);

        return response.writeWith(Flux.just(buffer));
    }
}
