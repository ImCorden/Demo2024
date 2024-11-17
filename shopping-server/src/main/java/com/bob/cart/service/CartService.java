package com.bob.cart.service;

import com.bob.cart.domain.Cart;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 购物车 服务类
 * </p>
 *
 * @author Bob
 * @since 2024-11-04
 */
public interface CartService extends IService<Cart> {

    /**
     * 按照StudentId查找购物车，没有的时候进行创建
     * <p>
     * @params : [studentId]
     * @return : com.bob.cart.domain.Cart
     **/
    Cart findCartOrCreateByStudentId(Long studentId);
}
