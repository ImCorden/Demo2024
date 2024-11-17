package com.bob.study.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.N;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @ClassName : StudyPlanCourseSelect
 * @Description : 添加购物车item BO
 *
 *
 * @Author : Bob
 * @Date : 2024/11/7 18:24
 * @Version : 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "添加购物车item BO")
public class StudyPlanCourseSelectBO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1868515553866592450L;

    @Schema(description = "学生ID")
    private Long studentId;

    @Schema(description = "所选课程Id")
    private List<Long> studyPlanCourseIds;

}
