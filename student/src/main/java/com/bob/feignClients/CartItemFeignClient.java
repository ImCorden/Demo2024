package com.bob.feignClients;


import com.bob.commontools.pojo.JsonResult;
import com.bob.commontools.pojo.bo.StudyPlanCourseCartItemBO;
import com.bob.feignClients.fallbackFactory.CartItemFeignClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @ClassName : CartItemFeignClient
 * @Description : TODO
 *
 *
 * @Author : Bob
 * @Date : 2024/11/8 09:47
 * @Version : 1.0
 **/
@FeignClient(
        name = "ShoppingServer",
        fallbackFactory = CartItemFeignClientFallbackFactory.class)
public interface CartItemFeignClient {

    /**
     * 向购物车加商品
     * <p>
     * @params : [itemBOList]
     * @return : com.bob.commontools.pojo.JsonResult
     **/
    @PostMapping(
            value = "/cart/cartItem/addItemToCart",
            headers = "Content-Type=application/json;charset=UTF-8")
    Boolean addCartItem(List<StudyPlanCourseCartItemBO> itemBOList);
}
