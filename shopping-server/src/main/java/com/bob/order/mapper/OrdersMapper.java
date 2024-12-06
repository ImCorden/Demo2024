package com.bob.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bob.order.domain.Orders;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author Bob
 * @since 2024-11-04
 */
public interface OrdersMapper extends BaseMapper<Orders> {


    @Delete("delete from orders where order_id > 1")
    int cleanMockData();

}
