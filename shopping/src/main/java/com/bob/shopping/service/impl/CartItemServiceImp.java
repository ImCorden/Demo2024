package com.bob.shopping.service.impl;

import com.bob.shopping.domain.CartItem;
import com.bob.shopping.mapper.CartItemMapper;
import com.bob.shopping.service.CartItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 购物车商品详情 服务实现类
 * </p>
 *
 * @author Bob
 * @since 2024-11-01
 */
@Service
public class CartItemServiceImp extends ServiceImpl<CartItemMapper, CartItem> implements CartItemService {

}
