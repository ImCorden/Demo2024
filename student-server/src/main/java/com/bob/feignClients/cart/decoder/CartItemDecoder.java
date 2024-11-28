package com.bob.feignClients.cart.decoder;


import com.bob.commontools.pojo.JsonResult;
import com.bob.commontools.utils.GsonHelper;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.Decoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName : CartItemDecoder
 * @Description : 只有在Http响应码在400以下时候才会使用Decoder进行处理
 * @Author : Bob
 * @Date : 2024/11/10 09:57
 * @Version : 1.0
 **/
@Slf4j
public class CartItemDecoder implements Decoder {

    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
        // 解析feign调用返回结果
        String body = null;
        try {
            body = Util.toString(response.body().asReader(StandardCharsets.UTF_8));
        } catch (IOException e) {
            log.error("feign.IOException", e);
        }
        log.info("----------CartItem decoded: {}", body);

        // 返回对应结果（业务逻辑错误返回false）
        JsonResult jsonResult = GsonHelper.json2Object(body, JsonResult.class);
        return jsonResult.isOK();
    }
}
