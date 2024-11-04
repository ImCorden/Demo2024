package com.bob.order.service.impl;

import com.bob.order.domain.OrderItem;
import com.bob.order.mapper.OrderItemMapper;
import com.bob.order.service.OrderItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单内商品 服务实现类
 * </p>
 *
 * @author Bob
 * @since 2024-11-04
 */
@Service
public class OrderItemServiceImp extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {

}
