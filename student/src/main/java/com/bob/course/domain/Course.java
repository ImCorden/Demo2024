package com.bob.course.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 课程表
 * </p>
 *
 * @author Bob
 * @since 2024-11-04
 */
@Getter
@Setter
@TableName("course")
@Schema(name = "Course", description = "课程表")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    @TableId("id")
    private Long id;

    @Schema(description = "课程名字")
    @TableField("course_name")
    private String courseName;

    @Schema(description = "课程地址")
    @TableField("course_addr")
    private String courseAddr;

    @Schema(description = "课程缩略图")
    @TableField("course_img")
    private String courseImg;

    @Schema(description = "课程介绍")
    @TableField("course_info")
    private String courseInfo;

    @Schema(description = "课程时长")
    @TableField("course_time")
    private LocalTime courseTime;

    @Schema(description = "老师Id")
    @TableField("teacher_id")
    private Long teacherId;

    @Schema(description = "启用状态")
    @TableField("course_state")
    private String courseState;

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
