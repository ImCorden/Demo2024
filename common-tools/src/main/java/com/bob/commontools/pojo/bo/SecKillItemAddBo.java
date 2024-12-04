package com.bob.commontools.pojo.bo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName : SecKillCourseAddBo
 * @Description : TODO
 * @Author : Bob
 * @Date : 2024/12/2 18:44
 * @Version : 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SecKillItemAddBo implements Serializable {

    /**
     * 秒杀物品id
     */
    private Long itemId;

    /**
     * 秒杀物品类型
     */
    private String itemType;

    /**
     * 秒杀库存
     */
    private Integer secKillNum;
}
