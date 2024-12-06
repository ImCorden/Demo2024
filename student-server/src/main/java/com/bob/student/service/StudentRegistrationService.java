package com.bob.student.service;

import com.bob.commontools.pojo.bo.StudentRegistrationProvinceBO;
import com.bob.student.domain.StudentRegistration;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 学生报名资格 服务类
 * </p>
 *
 * @author Bob
 * @since 2024-11-04
 */
public interface StudentRegistrationService extends IService<StudentRegistration> {

    /**
     * 校验是否可以报名，可以报名，发送mq消息
     * <p>
     *
     * @return : boolean
     * @params : [studentRegistrationProvinceBO]
     **/
    boolean checkByIdentityCode(StudentRegistrationProvinceBO studentRegistrationProvinceBO);

    /**
     * 注册学生
     * <p>
     * @params : [studentRegistrationProvinceBO]
     * @return : void
     **/
    void registerStudent(StudentRegistrationProvinceBO studentRegistrationProvinceBO);

    /**
     * 清理mock数据
     * <p>
     * @params : [ids]
     * @return : boolean
     **/
    boolean cleanMockData(List<Long> ids);
}
