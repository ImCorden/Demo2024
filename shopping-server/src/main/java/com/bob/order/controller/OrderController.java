package com.bob.order.controller;

import com.bob.commontools.pojo.JsonResult;
import com.bob.commontools.utils.GsonHelper;
import com.bob.commontools.utils.RedisOperator;
import com.bob.order.domain.Order;
import com.bob.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author Bob
 * @since 2024-11-04
 */
@Tag(name = "OrderController")
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@RequestMapping("/order/order")
public class OrderController {

    private final OrderService orderService;
    private final RedisOperator redisOperator;

    @Operation(summary = "测试Redis")
    @PostMapping("testRedis")
    public JsonResult testRedis(@RequestBody Order order) {
        redisOperator.set("testOrder-"+ Thread.currentThread().toString(), GsonHelper.object2Json(order));
        return JsonResult.ok();
    }

}
