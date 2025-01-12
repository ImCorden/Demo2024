package com.bob.seataDemo.controller;

import com.bob.commontools.exception.SeataDemoException;
import com.bob.commontools.pojo.JsonResult;
import com.bob.seataDemo.domain.SeataDemo;
import com.bob.seataDemo.service.SeataDemoService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
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
 * Seata表用来实现分布式事务 前端控制器
 * </p>
 *
 * @author Bob
 * @since 2025-01-09
 */
@Tag(name = "SeataDemoController", description = "Seata表用来实现分布式事务 前端控制器")
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/seataDemo/seataDemo")
@Slf4j
public class SeataDemoController {

    private final SeataDemoService seataDemoService;

    /**
     * AT模式保存
     * <p>
     *
     * @return : com.bob.commontools.pojo.JsonResult
     * @params : [seataDemo]
     **/
    @PostMapping("ATBothSave")
    public JsonResult bothSave(@RequestBody SeataDemo seataDemo) {
        String xid = RootContext.getXID();
        log.info("当前事务xid:{}", xid);
        boolean save = seataDemoService.save(seataDemo);
        if (seataDemo.getAge() % 2 == 0) {
            throw new RuntimeException("模拟异常");
        }
        return JsonResult.ok();
    }

    /**
     * TTC模式更新
     * <p>
     * @params : [seataDemo]
     * @return : com.bob.commontools.pojo.JsonResult
     **/
    @PostMapping("TTCBothUpdate")
    public JsonResult bothUpdate(@RequestBody SeataDemo seataDemo) throws SeataDemoException {
        seataDemoService.ttcUpdate(seataDemo);
        return JsonResult.ok();
    }

}
