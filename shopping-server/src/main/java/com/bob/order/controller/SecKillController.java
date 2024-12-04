package com.bob.order.controller;


import cn.hutool.core.util.ObjectUtil;
import com.bob.aspect.StudentHolder;
import com.bob.commontools.exception.BizException;
import com.bob.commontools.pojo.JsonResult;
import com.bob.commontools.pojo.bo.SecKillItem;
import com.bob.core.SecKillRedisTools;
import com.bob.commontools.pojo.bo.SecKillItemAddBo;
import com.bob.commontools.pojo.bo.SecKillItemBo;
import com.bob.order.service.SecKillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @ClassName : SecKillController
 * @Description : TODO
 * @Author : Bob
 * @Date : 2024/12/2 19:57
 * @Version : 1.0
 **/
@Tag(name = "SecKillController")
@RestController
@RequestMapping("/order/secKill")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecKillController {

    private final SecKillService secKillService;
    private final SecKillRedisTools secKillRedisTools;

    @Operation(summary = "添加秒杀商品信息")
    @PostMapping("addSecKillItem")
    public JsonResult addSecKillItem(@RequestBody SecKillItemAddBo secKillItemAddBo) throws BizException {
        boolean res = secKillService.addSecKillItem(secKillItemAddBo);
        return res ? JsonResult.ok() : JsonResult.errorMsg("添加失败！");
    }


    @Operation(summary = "用户进行秒杀接口")
    @PostMapping("toSecKill")
    public JsonResult toSecKill(@RequestBody SecKillItem secKillItem) throws BizException {
        if (ObjectUtil.isNull(secKillItem.getSecKillId())) {
            throw new BizException("秒杀商品Id不能为空！");
        }
        if (ObjectUtil.isNull(secKillItem.getSecKillType())) {
            throw new BizException("未能获取秒杀类型！");
        }
        Long studentId = StudentHolder.getId();
        if (ObjectUtil.isNull(studentId)) {
            throw new BizException("学生Id不能为空！");
        }
        boolean res = secKillService.sendSecKillMsg(
                SecKillItemBo.builder()
                        .secKillId(secKillItem.getSecKillId())
                        .secKillType(secKillItem.getSecKillType())
                        .studentId(studentId)
                        .build()
        );
        return res ? JsonResult.ok() : JsonResult.errorMsg("秒杀失败");
    }

    @PostMapping("listSecKillItem")
    public JsonResult listSecKillItem(@RequestParam String secKillType) throws BizException {
        return JsonResult.ok(secKillService.listSecKillItem(secKillType));
    }
}
