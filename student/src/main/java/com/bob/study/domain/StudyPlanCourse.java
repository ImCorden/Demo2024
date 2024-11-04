package com.bob.study.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

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
public class StudyPlanCourse implements Serializable {

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

    @Schema(description = "乐观锁")
    @TableField("revision")
    @Version
    private Integer revision;

    @Schema(description = "创建人")
    @TableField("created_by")
    private Long createdBy;

    @Schema(description = "创建时间")
    @TableField("created_time")
    private LocalDateTime createdTime;

    @Schema(description = "更新人")
    @TableField("updated_by")
    private Long updatedBy;

    @Schema(description = "更新时间")
    @TableField("updated_time")
    private LocalDateTime updatedTime;

    @Schema(description = "删除标识(0未删除，1删除)")
    @TableField("del_flag")
    @TableLogic
    private String delFlag;
}
