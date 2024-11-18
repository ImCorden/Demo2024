package com.bob.cart.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bob.cart.domain.CartItem;
import com.bob.cart.service.CartItemService;
import com.bob.commontools.pojo.JsonResult;
import com.bob.commontools.pojo.bo.StudyPlanCourseCartItemBO;
import com.bob.core.config.aop.StudentHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 购物车商品详情 前端控制器
 * </p>
 *
 * @author Bob
 * @since 2024-11-04
 */
@Tag(name = "CartItem Controller")
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@RequestMapping("/cart/cartItem")
public class CartItemController {

    private final CartItemService cartItemService;

    /**
     * 向购物车加商品
     * <p>
     *
     * @return : com.bob.commontools.pojo.JsonResult
     * @params : [itemBOList]
     **/
    @PostMapping("addItemToCart")
    public JsonResult addCartItem(@RequestBody List<StudyPlanCourseCartItemBO> itemBOList) {
        Long studentId = StudentHolder.getId();
        // int i = 1/0;
        boolean res = cartItemService.addItemToCart(itemBOList);
        if (res) {
            return JsonResult.ok();
        }
        return JsonResult.errorMsg("保存失败");
    }

    /**
     * 按学生ID查购物车
     * <p>
     *
     * @return : com.bob.commontools.pojo.JsonResult
     * @params : [studentId]
     **/
    @Operation(summary = "按学生ID查购物车")
    @GetMapping("findByStudentId/{studentId}")
    public JsonResult findByStudentId(@PathVariable Long studentId) {
        return JsonResult.ok(
                cartItemService.list(new LambdaQueryWrapper<CartItem>().eq(CartItem::getStudentId, studentId))
        );
    }

}
