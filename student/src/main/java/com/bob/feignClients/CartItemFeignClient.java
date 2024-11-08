package com.bob.feignClients;


import com.bob.commontools.pojo.JsonResult;
import com.bob.commontools.pojo.bo.StudyPlanCourseCartItemBO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
@FeignClient(name = "ShoppingServer")
public interface CartItemFeignClient {

    /**
     * 向购物车加商品
     * <p>
     * @params : [itemBOList]
     * @return : com.bob.commontools.pojo.JsonResult
     **/
    @PostMapping("/cart/cartItem/addItemToCart")
    JsonResult addCartItem(List<StudyPlanCourseCartItemBO> itemBOList);
}
