package com.bob.student.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 学生报名资格 
 * </p>
 *
 * @author TC
 * @since 2021-09-01
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "学生报名更新Model")
public class StudentRegistrationProvinceBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 报名序列号
     */
    @Schema(description = "报名序列号")
    private String registrationNum;

    /**
     * 年度
     */
    @Schema(description = "年度")
    private String year;

    /**
     * 身份证
     */
    @Schema(description = "身份证")
    private String identityCode;

    /**
     * 姓名
     */
    @Schema(description = "姓名")
    private String trueName;

}
