package com.bob.feignClients.shoppingServer.seataDemo;


import com.bob.commontools.exception.SeataDemoException;
import com.bob.seataDemo.domain.SeataDemo;
import io.seata.core.exception.TransactionException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @InterfaceName : SeataDemoFeignClient
 * @Description : TODO
 * @Author : Bob
 * @Date : 2025/1/10 AM12:30
 * @Version : 1.0
 **/
@FeignClient(
        name = "shopping-server",
        contextId = "seataDemo",
        path = "/seataDemo/seataDemo",
        fallbackFactory = SeataDemoFeignClientFallbackFactory.class
)
public interface SeataDemoFeignClient {

    @PostMapping("ATBothSave")
    Boolean bothSave(@RequestBody SeataDemo seataDemo) throws TransactionException;

    @PostMapping("TTCBothUpdate")
    Boolean bothUpdate(@RequestBody SeataDemo seataDemo) throws SeataDemoException, TransactionException;
}
