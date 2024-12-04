package com.bob;

import cn.dev33.satoken.SaManager;
import com.bob.commontools.utils.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.bob.*.mapper")
@EnableTransactionManagement
@Slf4j
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
        // log.info("启动成功，Sa-Token 配置如下：{}", GsonUtils.toPrettyFormat(SaManager.getConfig()));
    }

}
