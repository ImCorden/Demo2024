package com.bob.cart.service.impl;

import com.bob.cart.domain.Cart;
import com.bob.cart.mapper.CartMapper;
import com.bob.cart.service.CartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
