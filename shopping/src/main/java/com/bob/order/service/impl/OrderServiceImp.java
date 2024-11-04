package com.bob.order.service.impl;

import com.bob.order.domain.Order;
import com.bob.order.mapper.OrderMapper;
import com.bob.order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author Bob
 * @since 2024-11-04
 */
@Service
public class OrderServiceImp extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
