package com.bob.config;


/**
 * @ClassName : SaLogForSlf4j
 * @Description : 将 Sa-Token log 信息转接到 Slf4j 将 Sa-Token log 信息转接到 Slf4j
 *
 *
 * @Author : Bob
 * @Date : 2024/11/27 18:59
 * @Version : 1.0
 **/

import cn.dev33.satoken.log.SaLog;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SaLogForSlf4jConfig implements SaLog {

    @Override
    public void trace(String str, Object... args) {
        log.trace(str, args);
    }
    @Override
    public void debug(String str, Object... args) {
        log.debug(str, args);
    }
    @Override
    public void info(String str, Object... args) {
        log.info(str, args);
    }
    @Override
    public void warn(String str, Object... args) {
        log.warn(str, args);
    }
    @Override
    public void error(String str, Object... args) {
        log.error(str, args);
    }
    @Override
    public void fatal(String str, Object... args) {
        log.error(str, args);
    }
}
