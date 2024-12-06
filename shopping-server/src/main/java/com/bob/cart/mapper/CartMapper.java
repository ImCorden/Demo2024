package com.bob.cart.mapper;

import com.bob.cart.domain.Cart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 购物车 Mapper 接口
 * </p>
 *
 * @author Bob
 * @since 2024-11-04
 */
public interface CartMapper extends BaseMapper<Cart> {

    @Delete("delete from cart where cart.cart_id > 1")
    public int cleanMockData();

}
