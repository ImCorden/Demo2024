package com.bob.order.controller;

 
import com.bob.commontools.pojo.JsonResult;
import com.bob.commontools.pojo.enums.YesOrNo;
import com.bob.commontools.utils.GsonUtils;
import com.bob.commontools.utils.RedisUtil;
import com.bob.order.domain.Orders;
import com.bob.order.service.OrdersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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
public class OrdersController {

    private final OrdersService ordersService;
    private final RedisUtil redisUtil;

    @Operation(summary = "测试Redis")
    @PostMapping("testRedis")
    public JsonResult testRedis(@RequestBody Orders order) {
        redisUtil.set("testOrder-"+ Thread.currentThread().toString(), GsonUtils.object2Json(order));
        return JsonResult.ok();
    }

    @Operation(summary = "测试Redis")
    @GetMapping("test")
    public JsonResult test() {
         Orders order = Orders.builder()
                .payState(YesOrNo.NO.type)
                .studentId(1L)
                .payTime(LocalDateTime.now())
                .build();
         ordersService.save(order);
         return JsonResult.ok();
    }


}
