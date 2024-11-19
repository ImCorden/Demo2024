package com.bob.core.globalExceptionHandler;


import com.bob.commontools.pojo.JsonResult;
import com.bob.commontools.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ClassName : globalExceptionHandler
 * @Description : 全局错误处理
 * @Author : Bob
 * @Date : 2024/11/10 10:08
 * @Version : 1.0
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BusinessException.class)
    public JsonResult businessExceptionHandler(BusinessException exception) {
        // exception.printStackTrace();
        return JsonResult.errorCodeAndMsg(exception.getCode(), exception.getMessage());
    }

    @ExceptionHandler(value = RuntimeException.class)
    public JsonResult runTimeExceptionHandler(RuntimeException exception) {
        // exception.printStackTrace();
        return JsonResult.errorMsg(exception.getMessage());
    }
}
