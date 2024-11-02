package com.bob.shopping.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * 订单表
 * </p>
 *
 * @author Bob
 * @since 2024-11-01
 */
@Getter
@Setter
@TableName("orders")
@Schema(name = "Orders", description = "订单表")
public class Orders implements Serializable {

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
    private LocalDateTime payTime;

    @Schema(description = "订单关闭时间")
    @TableField("close_time")
    private LocalDateTime closeTime;

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
