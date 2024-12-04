package com.bob.cart.service.impl;

import com.bob.cart.domain.Cart;
import com.bob.cart.domain.CartItem;
import com.bob.cart.mapper.CartItemMapper;
import com.bob.cart.service.CartItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bob.cart.service.CartService;
import com.bob.commontools.pojo.bo.StudyPlanCourseCartItemBO;
import com.bob.aspect.StudentHolder;
import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 购物车商品详情 服务实现类
 * </p>
 *
 * @author Bob
 * @since 2024-11-04
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CartItemServiceImp extends ServiceImpl<CartItemMapper, CartItem> implements CartItemService {

    private final CartService cartService;

    /**
     * 向购物车加商品
     * <p>
     *
     * @return : boolean
     * @params : [itemBOList]
     **/
    @Override
    public boolean addItemToCart(List<StudyPlanCourseCartItemBO> itemBOList) {
        Preconditions.checkNotNull(itemBOList, "不能为空");
        Long studentId = StudentHolder.getId();
        // 购物车
        Cart cart = cartService.findCartOrCreateByStudentId(studentId);
        Long cartId = cart.getCartId();
        List<CartItem> saveItems = itemBOList.stream()
                .map(bo -> CartItem.builder()
                        .cartId(cartId)
                        .studentId(studentId)
                        .itemId(bo.getItemId())
                        .itemNum(1)
                        .itemName(bo.getItemName())
                        .itemPicUrl(bo.getItemPicUrl())
                        .itemPrice(bo.getItemPrice())
                        .itemType(bo.getItemType())
                        .build())
                .toList();
        return this.saveBatch(saveItems);
    }
}
