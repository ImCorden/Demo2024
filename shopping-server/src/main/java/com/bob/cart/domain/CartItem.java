package com.bob.cart.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * <p>
 * 购物车商品详情
 * </p>
 *
 * @author Bob
 * @since 2024-11-04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("cart_item")
@Schema(name = "CartItem", description = "购物车商品详情")
public class CartItem implements Serializable {

    @Serial
    private static final long serialVersionUID = 5268040018642406564L;

    @Schema(description = "主键")
    @TableId("cart_item_id")
    private Long cartItemId;

    @Schema(description = "学生id")
    @TableField("student_id")
    private Long studentId;

    @Schema(description = "购物车id")
    @TableField("cart_id")
    private Long cartId;

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

    @Schema(description = "物品数量")
    @TableField("item_num")
    private Integer itemNum;

    @Schema(description = "乐观锁")
    @TableField(value = "revision",fill = FieldFill.INSERT_UPDATE)
    @Version
    private Integer revision;

    @Schema(description = "创建人")
    @TableField(value = "created_by", fill = FieldFill.INSERT)
    private Long createdBy;

    @Schema(description = "创建时间")
    @TableField(value = "created_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh",timezone = "GMT+8")
    private LocalDateTime createdTime;

    @Schema(description = "更新人")
    @TableField(value = "updated_by", fill = FieldFill.UPDATE)
    private Long updatedBy;

    @Schema(description = "更新时间")
    @TableField(value = "updated_time", fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh",timezone = "GMT+8")
    private LocalDateTime updatedTime;

    @Schema(description = "删除标识(0未删除，1删除)")
    @TableField("del_flag")
    @TableLogic
    private String delFlag;
}
