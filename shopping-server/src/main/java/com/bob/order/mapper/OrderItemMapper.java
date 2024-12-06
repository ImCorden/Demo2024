package com.bob.order.mapper;

import com.bob.order.domain.OrderItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 订单内商品 Mapper 接口
 * </p>
 *
 * @author Bob
 * @since 2024-11-04
 */
public interface OrderItemMapper extends BaseMapper<OrderItem> {

    @Delete("delete from order_item where order_item.order_item_id > 1")
    int cleanMockData();

}
