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
 * 订单内商品
 * </p>
 *
 * @author Bob
 * @since 2024-11-04
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("order_item")
@Schema(name = "OrderItem", description = "订单内商品")
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "订单内物品id")
    @TableId("order_item_id")
    private Long orderItemId;

    @Schema(description = "订单id")
    @TableField("order_id")
    private Long orderId;

    @Schema(description = "学生Id")
    @TableField("student_id")
    private Long studentId;

    @Schema(description = "物品id")
    @TableField("item_id")
    private Long itemId;

    @Schema(description = "物品类型")
    @TableField("item_type")
    private String itemType;

    @Schema(description = "物品名称")
    @TableField("item_name")
    private String itemName;

    @Schema(description = "物品缩略图")
    @TableField("item_pic_url")
    private String itemPicUrl;

    @Schema(description = "物品价格")
    @TableField("item_price")
    private BigDecimal itemPrice;

    @Schema(description = "物品购买折扣价")
    @TableField("item_discount_price")
    private BigDecimal itemDiscountPrice;

    @Schema(description = "物品数量")
    @TableField("item_num")
    private Integer itemNum;

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
