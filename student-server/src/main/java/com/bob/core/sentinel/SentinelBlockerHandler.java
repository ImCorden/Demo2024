package com.bob.core.sentinel;


import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.bob.commontools.pojo.JsonResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * @ClassName : SentinelBlockerHandler
 * @Description : 自定义Sentinel限流返回值
 * @Author : Bob
 * @Date : 2024/11/10 17:12
 * @Version : 1.0
 **/
@Component
@Slf4j
public class SentinelBlockerHandler implements BlockExceptionHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws Exception {
        JsonResult res = null;
        if (e instanceof FlowException) {
            log.info("自定义限流返回");
            res = JsonResult.errorMsg("限流了");
        } else if (e instanceof DegradeException) {
            log.info("自定义降级返回");
            res = JsonResult.errorMsg("降级了");
        } else if (e instanceof ParamFlowException) {
            log.info("自定义热点参数限流返回");
            res = JsonResult.errorMsg("热点参数限流");
        } else if (e instanceof SystemBlockException) {
            log.info("自定义 系统规则（负载/...不满足要求） 返回");
            res = JsonResult.errorMsg("系统规则（负载/...不满足要求）");
        } else if (e instanceof AuthorityException) {
            log.info("自定义授权规则不通过返回");
            res = JsonResult.errorMsg("自定义授权规则不通过返回");
        }
        // http状态码
        httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setHeader("Content-Type", "application/json;charset=utf-8");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        // spring mvc自带的json操作工具，叫jackson
        new ObjectMapper().writeValue(httpServletResponse.getWriter(), res);
    }
}
