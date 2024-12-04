package com.bob.feignClients.shoppingServer.requestInterceptor;


import cn.hutool.core.util.ObjectUtil;
import com.bob.commontools.pojo.constants.BizConstants;
import com.bob.aspect.StudentHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

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

        // 打印template信息
        // log.info("CartItemFeignInterceptor working ! and template = {}"
        //         , new String(Objects.isNull(template.body()) ?
        //                 "null".getBytes() : template.body()
        //                 , StandardCharsets.UTF_8));
        Long studentId = StudentHolder.getId();
        if (ObjectUtil.isNotNull(studentId)) {
            template.header(BizConstants.HEADER_STUDENT_ID_KEY, String.valueOf(studentId));
        }
        log.info("--------CartItemFeignInterceptor 已经向Feign请求添加 Header : {}={}",BizConstants.HEADER_STUDENT_ID_KEY,studentId);
    }
}
