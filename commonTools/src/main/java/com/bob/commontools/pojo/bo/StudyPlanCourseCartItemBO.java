package com.bob.commontools.pojo.bo;


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
public class StudyPlanCourseCartItemBO implements Serializable {

    @Serial
    private static final long serialVersionUID = 348464917199457255L;

    private Long studentId;

    private Long itemId;

    private String itemType;

    private String itemName;

    private String itemPicUrl;

    private BigDecimal itemPrice;
}
