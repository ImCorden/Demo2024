package com.bob.commontools.pojo.vo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 学习计划课程
 * </p>
 *
 * @author Bob
 * @since 2024-11-04
 */
@Getter
@Setter
@TableName("study_plan_course")
@Schema(name = "StudyPlanCourse", description = "学习计划课程")
public class StudyPlanCourseInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    @TableField("id")
    private Long id;

    @Schema(description = "学习计划id")
    @TableField("study_plan_id")
    private Long studyPlanId;

    @Schema(description = "课程id")
    @TableField("course_id")
    private Long courseId;

    @Schema(description = "课程名字")
    @TableField("course_name")
    private String courseName;

    @Schema(description = "课程缩略图")
    @TableField("course_img")
    private String courseImg;

    @Schema(description = "学分")
    @TableField("unit")
    private BigDecimal unit;

    @Schema(description = "课程类别(A.B.C.D)")
    @TableField("study_plan_course_type")
    private String studyPlanCourseType;

    @Schema(description = "考试id")
    @TableField("exam_id")
    private Long examId;

    @Schema(description = "单价")
    @TableField("price")
    private BigDecimal price;
}
