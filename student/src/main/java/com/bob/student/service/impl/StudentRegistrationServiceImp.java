package com.bob.student.service.impl;

import com.bob.student.domain.StudentRegistration;
import com.bob.student.mapper.StudentRegistrationMapper;
import com.bob.student.service.StudentRegistrationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 学生报名资格 服务实现类
 * </p>
 *
 * @author Bob
 * @since 2024-11-01
 */
@Service
public class StudentRegistrationServiceImp extends ServiceImpl<StudentRegistrationMapper, StudentRegistration> implements StudentRegistrationService {

}
