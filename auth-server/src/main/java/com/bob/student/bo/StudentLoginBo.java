package com.bob.student.bo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName : StudentLoginBo
 * @Description : TODO
 *
 *
 * @Author : Bob
 * @Date : 2024/11/26 15:38
 * @Version : 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentLoginBo implements Serializable {

    private static final long serialVersionUID = 1006265872651680204L;

    private String identityCode;

    private String password;

    private String deviceType;
}
