package com.bob.shopping.service.impl;

import com.bob.shopping.domain.OrderItem;
import com.bob.shopping.mapper.OrderItemMapper;
import com.bob.shopping.service.OrderItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单内商品 服务实现类
 * </p>
 *
 * @author Bob
 * @since 2024-11-01
 */
@Service
public class OrderItemServiceImp extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {

}
