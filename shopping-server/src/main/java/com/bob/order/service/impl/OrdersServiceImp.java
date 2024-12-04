package com.bob.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bob.commontools.exception.BizException;
import com.bob.commontools.pojo.constants.BizConstants;
import com.bob.commontools.pojo.constants.RedisConstants;
import com.bob.commontools.pojo.enums.PayState;
import com.bob.commontools.pojo.enums.YesOrNo;
import com.bob.commontools.pojo.bo.SecKillItemBo;
import com.bob.commontools.pojo.vo.StudyPlanCourseInfoVO;
import com.bob.commontools.pojo.vo.StudyPlanInfoVO;
import com.bob.commontools.utils.GsonUtils;
import com.bob.commontools.utils.RedisUtil;
import com.bob.order.domain.OrderItem;
import com.bob.order.domain.Orders;
import com.bob.order.mapper.OrdersMapper;
import com.bob.order.service.OrderItemService;
import com.bob.order.service.OrdersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author Bob
 * @since 2024-11-04
 */
@Service
@Slf4j
@Transactional(rollbackFor = RuntimeException.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrdersServiceImp extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

    private final RedisUtil redisUtil;
    private final OrderItemService orderItemService;

    @Override
    public boolean createSecKillOrder(SecKillItemBo secKillItemBo) throws BizException {
        Orders order = Orders.builder()
                .studentId(secKillItemBo.getStudentId())
                .orderState(PayState.OPEN.type)
                .payState(YesOrNo.NO.type)
                .payTime(LocalDateTime.now())
                .build();
        OrderItem orderItem = null;
        switch (secKillItemBo.getSecKillType()){
            case BizConstants.SEC_KILL_TYPE_COURSE -> {
                String key = RedisConstants.REDIS_SEC_KILL_ITEM_COURSE_INFO_KEY_PREFIX + secKillItemBo.getSecKillId();
                StudyPlanCourseInfoVO course = GsonUtils.json2Object(redisUtil.get(key), StudyPlanCourseInfoVO.class);
                orderItem = OrderItem.builder()
                        .studentId(secKillItemBo.getStudentId())
                        .itemId(course.getId())
                        .itemType(BizConstants.SEC_KILL_TYPE_COURSE)
                        .itemName(course.getCourseName())
                        .itemPicUrl(course.getCourseImg())
                        .itemPrice(course.getPrice())
                        .itemDiscountPrice(BigDecimal.ZERO)
                        .itemNum(1)
                        .build();
                order.setTotalPrice(course.getPrice());
            }
            case BizConstants.SEC_KILL_TYPE_PLAN -> {
                 String key = RedisConstants.REDIS_SEC_KILL_ITEM_PLAN_INFO_KEY_PREFIX + secKillItemBo.getSecKillId();
                StudyPlanInfoVO plan = GsonUtils.json2Object(redisUtil.get(key), StudyPlanInfoVO.class);
                orderItem = OrderItem.builder()
                        .studentId(secKillItemBo.getStudentId())
                        .itemId(plan.getId())
                        .itemType(BizConstants.SEC_KILL_TYPE_PLAN)
                        .itemName(plan.getPlanName())
                        .itemPicUrl(null)
                        .itemPrice(plan.getPrice())
                        .itemDiscountPrice(BigDecimal.ZERO)
                        .itemNum(1)
                        .build();
                order.setTotalPrice(plan.getPrice());
            }
        }
        boolean resOrder = this.save(order);
        Optional.ofNullable(orderItem).ifPresent(oi -> oi.setOrderId(order.getOrderId()));
        boolean resOrderItem = orderItemService.save(orderItem);
        if (resOrder & resOrderItem) {
            // TODO 发送延时关单消息MQ
            return true;
        }
        throw new BizException("保存订单失败！");
    }
}
