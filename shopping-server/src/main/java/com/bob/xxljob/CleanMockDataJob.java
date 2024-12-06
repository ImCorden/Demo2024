package com.bob.xxljob;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bob.cart.mapper.CartItemMapper;
import com.bob.cart.mapper.CartMapper;
import com.bob.cart.service.CartItemService;
import com.bob.cart.service.CartService;
import com.bob.order.domain.OrderItem;
import com.bob.order.mapper.OrderItemMapper;
import com.bob.order.mapper.OrdersMapper;
import com.bob.order.service.OrderItemService;
import com.bob.order.service.OrdersService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName : FristJob
 * @Description : TODO
 * @Author : Bob
 * @Date : 2024/12/5 10:34
 * @Version : 1.0
 **/
@Slf4j
@Data
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CleanMockDataJob {

    private final OrdersService ordersService;
    private final OrderItemService orderItemService;
    private final CartService cartService;
    private final CartItemService cartItemService;
    // 懒得写了，就这样吧
    private final OrdersMapper ordersMapper;
    private final OrderItemMapper orderItemMapper;
    private final CartMapper cartMapper;
    private final CartItemMapper cartItemMapper;


    @XxlJob(value = "mockDataCleanHandler")
    public void execute() {
        // student-server使用分片任务，shopping不使用分片任务，属性用不上
        // int index = XxlJobHelper.getShardIndex();
        // int total = XxlJobHelper.getShardTotal();
        // String jobParam = XxlJobHelper.getJobParam();
        // String jobLogFileName = XxlJobHelper.getJobLogFileName();
        // log.info("jobLogFileName:{},index = {},total={},param={}", jobLogFileName, index, total, jobParam);
        if (orderItemService.count() > 0) {
            int res = orderItemMapper.cleanMockData();
            if (res > 0) {
                XxlJobHelper.log("orderItem清理完成： 已经清理:{}条数据！", res);
            }
        } else {
            XxlJobHelper.log("[数据库]没有需要清理的orderItem数据！");
        }
        if (ordersService.count() > 0) {
            int res = ordersMapper.cleanMockData();
            if (res > 0) {
                XxlJobHelper.log("orders清理完成： 已经清理:{}条数据！", res);
            }
        } else {
            XxlJobHelper.log("[数据库]没有需要清理的orders数据！");

        }
        if (cartItemService.count() > 0) {
            int res = cartItemMapper.cleanMockData();
            if (res > 0) {
                XxlJobHelper.log("cartItem清理完成： 已经清理:{}条数据！", res);
            }
        } else {
            XxlJobHelper.log("[数据库]没有需要清理的cartItem数据！");

        }
        if (cartService.count() > 0) {
            int res = cartMapper.cleanMockData();
            if (res > 0) {
                XxlJobHelper.log("cart清理完成： 已经清理:{}条数据！", res);
            }
        } else {
            XxlJobHelper.log("[数据库]没有需要清理的cart数据！");
        }
    }
}
