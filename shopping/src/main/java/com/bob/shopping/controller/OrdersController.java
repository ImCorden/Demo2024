package com.bob.shopping.controller;

import com.bob.commontools.pojo.JsonResult;
import com.bob.shopping.domain.Orders;
import com.bob.shopping.service.OrdersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author Bob
 * @since 2024-11-01
 */
@Tag(name = "订单 Controller")
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/shopping/orders")
public class OrdersController {

    private final OrdersService ordersService;


    /**
     * @description : TODO
     * @params : []
     * @return : com.bob.commontools.pojo.JsonResult
     * @date : 2024/11/2 21:52
     **/
    @Operation(summary = "list 订单")
    @GetMapping("list")
    public JsonResult list() {
        return JsonResult.ok(
                Optional.ofNullable(ordersService.list())
                        .orElse(new ArrayList<>())
        );
    }

}
