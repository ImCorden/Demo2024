package com.bob.commontools.pojo.constants;


import lombok.Data;

/**
 * @ClassName : Constants
 * @Description : TODO
 *
 *
 * @Author : Bob
 * @Date : 2024/11/18 22:39
 * @Version : 1.0
 **/
@Data
public class BizConstants {
    //---------------- Http ---------------------
    /**
     * 透传StudentID的Header key名称
     */
    public static final String HEADER_STUDENT_ID_KEY = "X-Custom-Field-Student-Id";


    // 秒杀
    public static final String SEC_KILL_TYPE_COURSE = "COURSE"; // 课程
    public static final String SEC_KILL_TYPE_PLAN = "PLAN"; // 计划
}
