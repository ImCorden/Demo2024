package com.bob.cart.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * <p>
 * 购物车
 * </p>
 *
 * @author Bob
 * @since 2024-11-04
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("cart")
@Schema(name = "Cart", description = "购物车")
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    @TableId("cart_id")
    private Long cartId;

    @Schema(description = "学生id")
    @TableField("student_id")
    private Long studentId;

    @Schema(description = "乐观锁")
    @TableField(value = "revision",fill = FieldFill.INSERT_UPDATE)
    @Version
    private Integer revision;

    @Schema(description = "创建人")
    @TableField(value = "created_by",fill = FieldFill.INSERT)
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
