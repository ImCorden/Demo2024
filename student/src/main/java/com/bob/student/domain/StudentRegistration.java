package com.bob.student.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 学生报名资格
 * </p>
 *
 * @author Bob
 * @since 2024-11-01
 */
@Getter
@Setter
@TableName("student_registration")
@Schema(name = "StudentRegistration", description = "学生报名资格")
public class StudentRegistration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    @TableId("id")
    private Long id;

    @Schema(description = "学生id")
    @TableField("student_id")
    private Long studentId;

    @Schema(description = "身份证")
    @TableField("identity_code")
    private String identityCode;

    @Schema(description = "报名序列号")
    @TableField("registration_num")
    private String registrationNum;

    @Schema(description = "年度")
    @TableField("year")
    private String year;

    @Schema(description = "是否使用")
    @TableField("used")
    private String used;

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
