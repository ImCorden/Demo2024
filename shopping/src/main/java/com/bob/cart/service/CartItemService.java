package com.bob.cart.service;

import com.bob.cart.domain.CartItem;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bob.commontools.pojo.bo.StudyPlanCourseCartItemBO;

import java.util.List;

/**
 * <p>
 * 购物车商品详情 服务类
 * </p>
 *
 * @author Bob
 * @since 2024-11-04
 */
public interface CartItemService extends IService<CartItem> {
    /**
     * 向购物车加商品
     * <p>
     *
     * @return : boolean
     * @params : [itemBOList]
     **/
    boolean addItemToCart(List<StudyPlanCourseCartItemBO> itemBOList);
}
