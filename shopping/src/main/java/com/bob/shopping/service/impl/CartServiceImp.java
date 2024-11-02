package com.bob.shopping.service.impl;

import com.bob.shopping.domain.Cart;
import com.bob.shopping.mapper.CartMapper;
import com.bob.shopping.service.CartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 购物车 服务实现类
 * </p>
 *
 * @author Bob
 * @since 2024-11-01
 */
@Service
public class CartServiceImp extends ServiceImpl<CartMapper, Cart> implements CartService {

}
