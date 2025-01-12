package com.bob.seataDemo.controller;

import com.bob.commontools.exception.SeataDemoException;
import com.bob.commontools.pojo.JsonResult;
import com.bob.feignClients.shoppingServer.seataDemo.SeataDemoFeignClient;
import com.bob.seataDemo.domain.SeataDemo;
import com.bob.seataDemo.service.SeataDemoService;
import groovy.util.logging.Slf4j;
import io.seata.core.exception.TransactionException;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * Seata表用来实现分布式事务 前端控制器
 * </p>
 *
 * @author Bob
 * @since 2025-01-09
 */
@Tag(name = "SeataDemoController", description = "Seata表用来实现分布式事务 前端控制器")
@RestController
@RequestMapping("/seataDemo/seataDemo")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class SeataDemoController {

    private final SeataDemoService seataDemoService;

    private final SeataDemoFeignClient seataDemoFeignClient;

    
    /**
     * AT模式保存
     * <p>
     * @params : [seataDemo]
     * @return : com.bob.commontools.pojo.JsonResult
     **/
    @PostMapping("ATBothSave")
    @GlobalTransactional(
            name = "ATBothSave",
            rollbackFor = Exception.class
    )
    public JsonResult bothSave(@RequestBody SeataDemo seataDemo) throws TransactionException {
        boolean save = seataDemoService.save(seataDemo);
        Boolean feignResult = seataDemoFeignClient.bothSave(seataDemo);
        if (save && feignResult) {
            return JsonResult.ok();
        }
        return JsonResult.errorMsg("保存失败");
    }

    /**
     * TTC模式更新
     * <p>
     * @params : [seataDemo]
     * @return : com.bob.commontools.pojo.JsonResult
     **/
    @PostMapping("TTCBothUpdate")
    @GlobalTransactional(
            name = "TTCBothUpdate",
            rollbackFor = Exception.class,
            lockRetryTimes = 2,
            timeoutMills = 5000
    )
    public JsonResult bothUpdate(@RequestBody SeataDemo seataDemo) throws SeataDemoException, TransactionException {
        Boolean res = seataDemoService.ttcUpdate(seataDemo);
        Boolean feignRes = seataDemoFeignClient.bothUpdate(seataDemo);
        if (res && feignRes) {
            return JsonResult.ok();
        }
        return JsonResult.errorMsg("更新失败");
    }

}
