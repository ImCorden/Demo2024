package com.bob.cart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bob.cart.domain.Cart;
import com.bob.cart.mapper.CartMapper;
import com.bob.cart.service.CartService;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <p>
 * 购物车 服务实现类
 * </p>
 *
 * @author Bob
 * @since 2024-11-04
 */
@Service
public class CartServiceImp extends ServiceImpl<CartMapper, Cart> implements CartService {
    /**
     * 按照StudentId查找购物车，没有的时候进行创建
     * <p>
     *
     * @return : com.bob.cart.domain.Cart
     * @params : [studentId]
     **/
    @Override
    public Cart findCartOrCreateByStudentId(Long studentId) {
        Preconditions.checkNotNull(studentId, "studentId 不能为空");

        Cart cart = this.getOne(new LambdaQueryWrapper<Cart>()
                .select(Cart::getCartId, Cart::getStudentId)
                .eq(Cart::getStudentId, studentId));

        if (Optional.ofNullable(cart).isEmpty()) {
            Cart cartSave = Cart.builder().studentId(studentId).build();
            if (this.save(cartSave)){
                return cartSave;
            }else {
                throw new RuntimeException("保存购物车");
            }
        }
        return cart;
    }
}
