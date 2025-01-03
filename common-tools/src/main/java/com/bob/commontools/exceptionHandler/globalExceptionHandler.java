package com.bob.commontools.exceptionHandler;


import com.bob.commontools.exception.BizException;
import com.bob.commontools.pojo.JsonResult;
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
public class globalExceptionHandler {

    @ExceptionHandler(value = BizException.class)
    public JsonResult businessExceptionHandler(BizException exception) {
        // exception.printStackTrace();
        return JsonResult.errorCodeAndMsg(exception.getCode(), exception.getMessage());
    }

    // @ExceptionHandler(value = RuntimeException.class)
    public JsonResult runTimeExceptionHandler(RuntimeException exception) {
        // exception.printStackTrace();
        return JsonResult.errorMsg(exception.getMessage());
    }

}
