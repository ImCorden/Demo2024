package com.bob.stream;


import com.bob.commontools.exception.BizException;
import com.bob.commontools.utils.GsonUtils;
import com.bob.commontools.pojo.bo.SecKillItemBo;
import com.bob.order.service.OrdersService;
import com.bob.order.service.SecKillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.support.ErrorMessage;

import java.util.function.Consumer;

/**
 * @ClassName : SecKillConsumer
 * @Description : TODO
 * @Author : Bob
 * @Date : 2024/12/3 10:54
 * @Version : 1.0
 **/
@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class SecKillConsumer {

    private final SecKillService secKillService;
    private final OrdersService ordersService;

    /**
     * 消费者
     * <p>
     *
     * @return : java.util.function.Consumer<com.bob.commontools.pojo.bo.SecKillCourseBo>
     * @params : []
     **/
    @Bean
    public Consumer<SecKillItemBo> toSecKill() {
        return message -> {
            // log.info("-------------------------------------------------------------toSecKillConsumer接到消息：{}", message);
            // throw new RuntimeException(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>error");
            // 查看是否已经秒杀成功
            boolean secKillSuccess = secKillService.isSecKillSuccess(message);
            if (!secKillSuccess) {
                // 没有秒杀过的进行秒杀
                boolean secKillRes = secKillService.toSecKillItem(message);
                if (secKillRes) {
                    // 秒杀成功加表示，创建订单
                    String token = secKillService.createOrderToken(message);
                    try {
                        boolean res = ordersService.createSecKillOrder(message);
                    } catch (BizException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
    }

    /**
     * 错误消费处理
     * 如需验证，请启用 {@link SecKillConsumer#toSecKill} 中的异常
     * <p>
     *
     * @return : java.util.function.Consumer<org.springframework.messaging.support.ErrorMessage>
     * @params : []
     **/
    @Bean
    public Consumer<ErrorMessage> myHandler() {
        return v -> {
            String msg = new String((byte[]) v.getOriginalMessage().getPayload());
            String localizedMessage = v.getPayload().getCause().getLocalizedMessage();
            String HeadersJson = GsonUtils.object2Json(v.getOriginalMessage().getHeaders());

            log.error("Registration Student Consumer 消费异常");
            log.error("原始消息：{}", msg);
            log.error("原始消息Headers：{}", HeadersJson);
            log.error("失败原因：{}", localizedMessage);
            log.error("END");
        };
    }
}
