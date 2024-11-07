package com.bob.order.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author Bob
 * @since 2024-11-04
 */
@Data
@NoArgsConstructor
@TableName("order")
@Schema(name = "Order", description = "订单表")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    @TableId("order_id")
    private Long orderId;

    @Schema(description = "学生Id")
    @TableField("student_id")
    private Long studentId;

    @Schema(description = "订单状态（OPEN，CLOSE）")
    @TableField("order_state")
    private String orderState;

    @Schema(description = "总价")
    @TableField("total_price")
    private BigDecimal totalPrice;

    @Schema(description = "支付状态(UNPAY,PAYED,CANCEL)")
    @TableField("pay_state")
    private String payState;

    @Schema(description = "支付类型")
    @TableField("pay_type")
    private String payType;

    @Schema(description = "支付时间")
    @TableField("pay_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh",timezone = "GMT+8")
    private LocalDateTime payTime;

    @Schema(description = "订单关闭时间")
    @TableField("close_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh",timezone = "GMT+8")
    private LocalDateTime closeTime;

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
