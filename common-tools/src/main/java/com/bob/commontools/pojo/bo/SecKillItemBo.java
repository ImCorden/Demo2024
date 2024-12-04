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
public class SecKillItemBo implements Serializable {

    /**
     * 学生Id
     */
    private Long studentId;

    /**
     * 设置的秒杀Id（非实际课程或者计划的ID）
     */
    private String secKillId;

    /**
     * 秒杀类型：COURSE，PLAN
     */
    private String secKillType;
}
