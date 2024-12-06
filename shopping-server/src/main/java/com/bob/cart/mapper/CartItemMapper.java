package com.bob.cart.mapper;

import com.bob.cart.domain.CartItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 购物车商品详情 Mapper 接口
 * </p>
 *
 * @author Bob
 * @since 2024-11-04
 */
public interface CartItemMapper extends BaseMapper<CartItem> {

    @Delete("delete from cart_item where cart_item.cart_item_id > 1")
    public int cleanMockData();

}
