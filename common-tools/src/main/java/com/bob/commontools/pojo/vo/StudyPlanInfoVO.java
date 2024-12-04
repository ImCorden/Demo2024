package com.bob.commontools.pojo.vo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 学习计划
 * </p>
 *
 * @author Bob
 * @since 2024-11-04
 */
@Data
@TableName("study_plan")
@Schema(name = "StudyPlan", description = "学习计划")
public class StudyPlanInfoVO implements Serializable {

    @Serial
    private static final long serialVersionUID = -5668291694715696923L;

    @Schema(description = "主键")
    @TableField("id")
    private Long id;

    @Schema(description = "计划名称")
    @TableField("plan_name")
    private String planName;

    @Schema(description = "计划年度")
    @TableField("plan_year")
    private String planYear;

    @Schema(description = "计划描述")
    @TableField("plan_desc")
    private String planDesc;

    @Schema(description = "考试id")
    @TableField("exam_id")
    private Long examId;

    @Schema(description = "考试类型(COURSE一课一考，PLAN大考)")
    @TableField("exam_type")
    private String examType;

    @Schema(description = "是否顺序学习(UNORDER否，ORDERED是)")
    @TableField("ordered_study_state")
    private String orderedStudyState;

    @Schema(description = "购买方式（PLAN计划，COURSE课程）")
    @TableField("sale_type")
    private String saleType;

    @Schema(description = "单价")
    @TableField("price")
    private BigDecimal price;

    @Schema(description = "启用状态")
    @TableField("start_state")
    private String startState;
}
