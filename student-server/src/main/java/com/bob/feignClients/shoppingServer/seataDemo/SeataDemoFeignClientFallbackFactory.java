package com.bob.feignClients.shoppingServer.seataDemo;


import com.bob.commontools.exception.SeataDemoException;
import com.bob.seataDemo.domain.SeataDemo;
import io.seata.core.context.RootContext;
import io.seata.core.exception.TransactionException;
import io.seata.tm.TransactionManagerHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @ClassName : CartItemFeignClientFallbackFactory
 * @Description : TODO
 * @Author : Bob
 * @Date : 2024/11/9 21:57
 * @Version : 1.0
 **/
@Component
@Slf4j
public class SeataDemoFeignClientFallbackFactory implements FallbackFactory<SeataDemoFeignClient> {
    @Override
    public SeataDemoFeignClient create(Throwable cause) {
        return new SeataDemoFeignClient() {
            @Override
            public Boolean bothSave(SeataDemo seataDemo) throws TransactionException {
                this.rollBack();
                return false;
            }

            @Override
            public Boolean bothUpdate(SeataDemo seataDemo) throws SeataDemoException, TransactionException {
                this.rollBack();
                return false;
            }

            private void rollBack() throws TransactionException {
                log.error("##FALLBACK FACTORY : CartItemFeignClient FallBack----------------------");
                log.error("##FALLBACK FACTORY : {}", String.valueOf(cause));
                log.error("##FALLBACK FACTORY : {}", cause.getMessage());
                // 进行手动回滚
                String xid = RootContext.getXID();
                TransactionManagerHolder.get().rollback(xid);
                log.error("##FALLBACK FACTORY : SeataDemoFeignClient bothSave rollback xid:{}", xid);
            }
        };
    }
}
