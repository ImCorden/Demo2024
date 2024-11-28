package com.bob.gateway.config;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName : SaTokenUrlPermission
 * @Description : 自定义路由规则
 * @Author : Bob
 * @Date : 2024/11/15 15:24
 * @Version : 1.0
 **/
@RefreshScope
@Component
@ConfigurationProperties("rule")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaTokenUrlRule {
    /**
     * 不要鉴权的路径
     */
    private List<String> excludes;

    /**
     * 不需要登录校验uri（如果路径在permission中存在，一定会拦截，因为没有登录                                                                                                                                 ）
     */
    private List<String> withOutLoginUri;

    /**
     * 请求路径对应的权限
     */
    private List<DynamicPermission> permissions;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DynamicPermission {
        /**
         * 资源路径
         */
        private String uri;

        /**
         * 权限
         */
        private String permission;
    }
}
