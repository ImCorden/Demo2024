package com.bob.shopping.service.impl;

import com.bob.shopping.domain.Orders;
import com.bob.shopping.mapper.OrdersMapper;
import com.bob.shopping.service.OrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author Bob
 * @since 2024-11-01
 */
@Service
public class OrdersServiceImp extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

}
