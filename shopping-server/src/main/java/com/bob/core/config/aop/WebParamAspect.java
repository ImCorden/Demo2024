package com.bob.core.config.aop;


import com.bob.commontools.pojo.BusinessConstants;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @ClassName : WebParamApect
 * @Description : TODO
 * @Author : Bob
 * @Date : 2024/11/18 16:40
 * @Version : 1.0
 **/
@Slf4j
@Component
@Aspect
@Order(-1)
public class WebParamAspect {


    @Pointcut("execution(* com.bob.*.controller.*.*(..))")
    private void pointcut() {
    }

    @Before("pointcut()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes != null) {
            String header = servletRequestAttributes.getRequest().getHeader(BusinessConstants.HEADER_STUDENT_ID_KEY);
            if (header != null) {
                StudentHolder.add(Long.parseLong(header));
            }
        }
        log.info("------------doBefore");
    }

    @After("pointcut()")
    public void doAfter(JoinPoint joinPoint) {
        StudentHolder.remove();
        log.info("------------doAfter");
    }

}
