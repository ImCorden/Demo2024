package com.bob.gateway.config.saToken;


/**
 * @ClassName : SaTokenConfigure
 * @Description : TODO
 * @Author : Bob
 * @Date : 2024/11/17 23:33
 * @Version : 1.0
 **/

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import com.bob.commontools.pojo.JsonResult;
import com.bob.commontools.utils.GsonHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * [Sa-Token 权限认证] 配置类
 *
 * @author click33
 */
@Slf4j
@Configuration
public class SaTokenConfigure {

    @Autowired
    private SaTokenUrlRule saTokenUrlRule;

    // 注册 Sa-Token全局过滤器
    @Bean
    public SaReactorFilter getSaReactorFilter() {
        log.info("---------- SaToken 全局Filter start ----------");

        SaReactorFilter saReactorFilter = new SaReactorFilter()
                // 拦截地址
                .addInclude("/**")    /* 拦截全部path */
                // 开放地址
                .setExcludeList(saTokenUrlRule.getExcludes()) /* 不能动态配置 */
                // 鉴权方法：每次访问进入
                .setAuth(obj -> {

                    // 排除不需要登录的资源后，验证是否登录
                    // 如果请求资源后面Permission校验中匹配，依旧会被拦截请求，因为没有登录就没有办法验证资源Permission
                    // 所以，Permission校验中，需要没有这里排除的所有路径对饮关系（比如：auth-server的校验，因为如果后面包括了auth的Permission，auth-server的登录接口就会被拦截）
                    if (CollUtil.isNotEmpty(saTokenUrlRule.getWithOutLoginUri())) {
                        log.info("----------当前WithOutLoginUri:{}", GsonHelper.object2Json(saTokenUrlRule.getWithOutLoginUri()));
                        SaRouter.match("/**")
                                .notMatch(saTokenUrlRule.getWithOutLoginUri())
                                .check(r -> StpUtil.checkLogin());
                    }

                    // 权限认证 -- 不同模块, 校验不同权限
                    if (CollUtil.isNotEmpty(saTokenUrlRule.getPermissions())) {
                        log.info("----------当前Permissions:{}", GsonHelper.object2Json(saTokenUrlRule.getPermissions()));
                        saTokenUrlRule.getPermissions().forEach(p -> {
                            // SaRouter.match("/user/**", r -> StpUtil.checkPermission("user"));
                            log.info("----------Permissions判断:{}", GsonHelper.object2Json(p));
                            SaRouter.match(p.getUri(), r -> StpUtil.checkPermission(p.getPermission()))
                                    .stop();// 最后的匹配过程，一旦匹配跳出后续匹配

                        });
                    }
                    // 更多匹配
                })
                // 异常处理方法：每次setAuth函数出现异常时进入
                .setError(e -> {
                    log.error("----------鉴权抛出问题:{}", e.getMessage());
                    // return SaResult.error(e.getMessage());
                    return GsonHelper.object2Json(JsonResult.errorMsg(e.getMessage()));
                })
                .setBeforeAuth(obj -> {
                    // ---------- 设置跨域响应头 ----------
                    SaHolder.getResponse()
                            // 允许指定域访问跨域资源
                            .setHeader("Access-Control-Allow-Origin", "*")
                            // 允许所有请求方式
                            .setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE")
                            // 有效时间
                            .setHeader("Access-Control-Max-Age", "3600")
                            // 允许的header参数
                            .setHeader("Access-Control-Allow-Headers", "*");

                    // 如果是预检请求，则立即返回到前端
                    SaRouter.match(SaHttpMethod.OPTIONS)
                            .free(r -> log.info("--------OPTIONS预检请求，不做处理"))
                            .back();
                });
        log.info("---------- SaToken 全局Filter end ----------");
        return saReactorFilter;
    }
}

