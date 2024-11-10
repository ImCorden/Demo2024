package com.bob.feignClients.cart.requestInterceptor;


import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @ClassName : CartItemFeignInterceptor
 * @Description : Feign Interceptor
 * @Author : Bob
 * @Date : 2024/11/10 09:51
 * @Version : 1.0
 **/
@Slf4j
public class CartItemFeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        log.info("CartItemFeignInterceptor working ! and template = {}"
                , new String(Objects.isNull(template.body()) ?
                        "null".getBytes() : template.body()
                        , StandardCharsets.UTF_8));
        // 传递令牌
        // RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes(); // 获取请求头参数
        // if (requestAttributes != null){
        //     HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        //     if (request != null){
        //         Enumeration<String> headerNames = request.getHeaderNames();
        //         while (headerNames.hasMoreElements()){
        //             String headerName = headerNames.nextElement();
        //             if ("authorization".equals(headerName)){
        //                 String headerValue = request.getHeader(headerName); // Bearer jwt
        //
        //                 //传递令牌
        //                 template.header(headerName,headerValue);
        //             }
        //         }
        //     }
        // }
    }
}
