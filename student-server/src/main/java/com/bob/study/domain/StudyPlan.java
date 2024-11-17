package com.bob.study.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
public class StudyPlan implements Serializable {

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

    @Schema(description = "乐观锁")
    @TableField(value = "revision",fill = FieldFill.INSERT_UPDATE)
    @Version
    private Integer revision;

    @Schema(description = "创建人")
    @TableField(value = "created_by",fill =FieldFill.INSERT)
    private Long createdBy;

    @Schema(description = "创建时间")
    @TableField(value = "created_time",fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh",timezone = "GMT+8")
    private LocalDateTime createdTime;

    @Schema(description = "更新人")
    @TableField(value = "updated_by",fill = FieldFill.UPDATE)
    private Long updatedBy;

    @Schema(description = "更新时间")
    @TableField(value = "updated_time",fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh",timezone = "GMT+8")
    private LocalDateTime updatedTime;

    @Schema(description = "删除标识(0未删除，1删除)")
    @TableField("del_flag")
    @TableLogic
    private String delFlag;
}
