package com.bob.feignClients.studentServer.decoder;


import com.bob.commontools.exception.BizException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName : CartItemDecoder
 * @Description : 只有在Http请求响应状态码在400以上时候才会使用ErrorDecoder进行处理
 * @Author : Bob
 * @Date : 2024/11/10 09:57
 * @Version : 1.0
 **/
@Slf4j
public class StudentServerErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
         // 解析feign调用返回结果
        String body = null;
        try {
            body = Util.toString(response.body().asReader(StandardCharsets.UTF_8));
        } catch (IOException e) {
            log.error("feign.IOException", e);
        }
        log.info("----------Course ErrorDecoder: {}", body);

        return new BizException(HttpStatus.BAD_REQUEST.value(), body);
    }
}
