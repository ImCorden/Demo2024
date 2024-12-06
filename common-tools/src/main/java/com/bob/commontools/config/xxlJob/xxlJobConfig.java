package com.bob.commontools.config.xxlJob;


import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName : xxlJobConfig
 * @Description : TODO
 * @Author : Bob
 * @Date : 2024/12/5 14:55
 * @Version : 1.0
 **/
@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix = "xxl.job")
public class xxlJobConfig {

    private String adminAddresses;

    private String accessToken;

    private Executor executor;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Executor {
        private String appName;

        private String ip;

        private Integer port;

        private String logPath;

        private Integer logRetentionDays;
    }


    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
        log.info(">>>>>>>>>>> xxl-job config init.");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(adminAddresses);
        xxlJobSpringExecutor.setAccessToken(accessToken);
        xxlJobSpringExecutor.setAppname(executor.getAppName());
        xxlJobSpringExecutor.setIp(executor.getIp());
        xxlJobSpringExecutor.setPort(executor.getPort());
        xxlJobSpringExecutor.setLogPath(executor.logPath);
        xxlJobSpringExecutor.setLogRetentionDays(executor.logRetentionDays);
        return xxlJobSpringExecutor;
    }
}
