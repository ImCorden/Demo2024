package com.bob.student.service.impl;


import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bob.commontools.pojo.enums.YesOrNo;
import com.bob.commontools.utils.GsonHelper;
import com.bob.core.pojo.Constant;
import com.bob.student.domain.Student;
import com.bob.student.service.StudentRoleService;
import com.bob.student.service.StudentService;
import com.bob.student.bo.StudentRegistrationProvinceBO;
import com.bob.student.domain.StudentRegistration;
import com.bob.student.mapper.StudentRegistrationMapper;
import com.bob.student.service.StudentRegistrationService;
import com.bob.stream.StreamProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 学生报名资格 服务实现类
 * </p>
 *
 * @author Bob
 * @since 2024-11-04
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class StudentRegistrationServiceImp extends ServiceImpl<StudentRegistrationMapper, StudentRegistration> implements StudentRegistrationService {

    private final StreamProducer streamProducer;
    private final StudentService studentService;
    private final StudentRoleService studentRoleService;

    /**
     * 校验是否可以报名，可以报名，发送mq消息
     * <p>
     *
     * @return : boolean
     * @params : [studentRegistrationProvinceBO]
     **/
    @Override
    public boolean checkByIdentityCode(StudentRegistrationProvinceBO studentRegistrationProvinceBO) {

        // 查询是否已经报名过
        List<StudentRegistration> res = this.list(new LambdaQueryWrapper<StudentRegistration>()
                .eq(StudentRegistration::getIdentityCode, studentRegistrationProvinceBO.getIdentityCode())
                .eq(StudentRegistration::getYear, studentRegistrationProvinceBO.getYear())
        );
        // 不存在发送Msg
        if (res.isEmpty()) {
            return streamProducer.sendSyncSingleMsg(GsonHelper.object2Json(studentRegistrationProvinceBO));
        }
        return false;
    }

    /**
     * 注册学生
     * <p>
     *
     * @return : void
     * @params : [studentRegistrationProvinceBO]
     **/
    @Override
    public void registerStudent(StudentRegistrationProvinceBO studentRegistrationProvinceBO) {
        // 保存学生信息
        List<Student> res = studentService.list(new LambdaQueryWrapper<Student>()
                .eq(Student::getIdentityCode, studentRegistrationProvinceBO.getIdentityCode()));
        Student stu = Student.builder().build();
        if (res.isEmpty()) {
            String salt = BCrypt.gensalt();
            String hashPwd = BCrypt.hashpw("123456", salt);
            stu =  Student.builder()
                    .identityCode(studentRegistrationProvinceBO.getIdentityCode())
                    .trueName(studentRegistrationProvinceBO.getTrueName())
                    .password(hashPwd)
                    .salt(salt)
                    .build();
            studentService.save(stu);
            // 保存默认角色
            studentRoleService.saveDefaultStudentRole(stu.getId());
            log.info(Constant.LOG_STYLE,"Saving Student And StudentRole to DB");
        }else {
            stu = res.get(0);
        }
        // 保存报名信息
        boolean save = this.save(StudentRegistration.builder()
                .studentId(stu.getId())
                .identityCode(studentRegistrationProvinceBO.getIdentityCode())
                .year(studentRegistrationProvinceBO.getYear())
                .registrationNum(studentRegistrationProvinceBO.getRegistrationNum())
                        .used(YesOrNo.NO.type)
                .build());
        log.info(Constant.LOG_STYLE,"Saving StudentRegistration to DB");
        if (!save) {
            log.error(Constant.LOG_STYLE,"保存出错，插入log，人工补偿");
        }
    }


}
