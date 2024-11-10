package com.bob.feignClients.fallbackFactory;


import com.bob.commontools.pojo.JsonResult;
import com.bob.commontools.pojo.bo.StudyPlanCourseCartItemBO;
import com.bob.feignClients.CartItemFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName : CartItemFeignClientFallbackFactory
 * @Description : TODO
 * @Author : Bob
 * @Date : 2024/11/9 21:57
 * @Version : 1.0
 **/
@Component
@Slf4j
public class CartItemFeignClientFallbackFactory implements FallbackFactory<CartItemFeignClient> {


    @Override
    public CartItemFeignClient create(Throwable cause) {
        return new CartItemFeignClient() {

            @Override
            public Boolean addCartItem(List<StudyPlanCourseCartItemBO> itemBOList) {
                log.error("##FALLBACK FACTORY : CartItemFeignClient FallBack----------------------");
                log.error("##FALLBACK FACTORY : {}", String.valueOf(cause));
                log.error("##FALLBACK FACTORY : {}", cause.getMessage());
                return false;
            }
        };
    }
}
