package com.bob.cart.service.impl;

import com.bob.cart.domain.CartItem;
import com.bob.cart.mapper.CartItemMapper;
import com.bob.cart.service.CartItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 购物车商品详情 服务实现类
 * </p>
 *
 * @author Bob
 * @since 2024-11-04
 */
@Service
public class CartItemServiceImp extends ServiceImpl<CartItemMapper, CartItem> implements CartItemService {

}
