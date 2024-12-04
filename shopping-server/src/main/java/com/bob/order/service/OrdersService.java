package com.bob.order.service;

import com.bob.commontools.exception.BizException;
import com.bob.commontools.pojo.bo.SecKillItemBo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bob.order.domain.Orders;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author Bob
 * @since 2024-11-04
 */
public interface OrdersService extends IService<Orders> {

    boolean createSecKillOrder(SecKillItemBo message) throws BizException;
}
