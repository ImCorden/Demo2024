package com.bob.commontools.pojo.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalTime;

/**
 * @ClassName : CourseInfoVO
 * @Description : TODO
 * @Author : Bob
 * @Date : 2024/12/2 22:11
 * @Version : 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseInfoVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 684241786942927936L;

    /** 主键 */
    private Long id;

    /** 课程名字 */
    private String courseName;

    /** 课程地址 */
    private String courseAddr;

    /** 课程缩略图 */
    private String courseImg;

    /** 课程介绍 */
    private String courseInfo;

    /** 课程时长 */
    @JsonFormat(pattern = "HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private LocalTime courseTime;

    /** 老师Id */
    private Long teacherId;

    /** 启用状态 */
    private String courseState;
}
