package com.bob.student.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 学生表
 * </p>
 *
 * @author Bob
 * @since 2024-11-04
 */
@Builder
@Getter
@Setter
@TableName("student")
@Schema(name = "Student", description = "学生表")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    @TableId("id")
    private Long id;

    @Schema(description = "真实姓名")
    @TableField("true_name")
    private String trueName;

    @Schema(description = "昵称")
    @TableField("nick_name")
    private String nickName;

    @Schema(description = "头像地址")
    @TableField("avatar_url")
    private String avatarUrl;

    @Schema(description = "身份证")
    @TableField("identity_code")
    private String identityCode;

    @Schema(description = "年龄")
    @TableField("age")
    private Integer age;

    @Schema(description = "乐观锁")
    @TableField(value = "revision",fill = FieldFill.INSERT_UPDATE)
    @Version
    private Integer revision;

    @Schema(description = "创建人")
    @TableField(value = "created_by",fill = FieldFill.INSERT)
    private Long createdBy;

    @Schema(description = "创建时间")
    @TableField(value = "created_time",fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    @Schema(description = "更新人")
    @TableField(value = "updated_by",fill = FieldFill.UPDATE)
    private Long updatedBy;

    @Schema(description = "更新时间")
    @TableField(value = "updated_time",fill = FieldFill.UPDATE)
    private LocalDateTime updatedTime;

    @Schema(description = "删除标识")
    @TableField("del_flag")
    @TableLogic
    private String delFlag;
}
