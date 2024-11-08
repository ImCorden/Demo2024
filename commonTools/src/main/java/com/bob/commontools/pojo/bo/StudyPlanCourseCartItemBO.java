package com.bob.commontools.pojo.bo;


import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName : StudyPlanCourseCartItemBO
 * @Description : TODO
 *
 *
 * @Author : Bob
 * @Date : 2024/11/7 23:43
 * @Version : 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "add to cart bo")
public class StudyPlanCourseCartItemBO implements Serializable {

    @Serial
    private static final long serialVersionUID = 348464917199457255L;

    @Schema(description = "学生id")
    private Long studentId;

    @Schema(description = "物品id")
    @TableField("item_id")
    private Long itemId;

    @Schema(description = "物品类型")
    private String itemType;

    @Schema(description = "物品名称")
    private String itemName;

    @Schema(description = "物品缩略图")
    private String itemPicUrl;

    @Schema(description = "物品价格")
    private BigDecimal itemPrice;
}
