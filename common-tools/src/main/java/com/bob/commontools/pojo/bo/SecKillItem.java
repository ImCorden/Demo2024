package com.bob.commontools.pojo.bo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.N;

import java.io.Serial;
import java.io.Serializable;

/**
 * @ClassName : SecKillItem
 * @Description : TODO
 *
 *
 * @Author : Bob
 * @Date : 2024/12/4 19:00
 * @Version : 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SecKillItem implements Serializable {

    @Serial
    private static final long serialVersionUID = 3978959584407031846L;

    /**
     * 秒杀ITEMId
     */
    private String secKillId;

    /**
     * 秒杀ITEM类型
     */
    private String secKillType;
}
