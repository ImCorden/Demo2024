package com.bob.aspect;


import java.lang.annotation.*;

/**
 * @InterfaceName : NoNeedStudentId
 * @Description : 用于标记不需要 WebParamAspect 切面拦截记录日志的方法
 * @Author : Bob
 * @Date : 2025/12/31 PM1:06
 * @Version : 1.0
 **/
@Target({ElementType.METHOD}) // 作用在方法上
@Retention(RetentionPolicy.RUNTIME) // 运行时有效
@Documented
public @interface UnnecessaryStudentId { }