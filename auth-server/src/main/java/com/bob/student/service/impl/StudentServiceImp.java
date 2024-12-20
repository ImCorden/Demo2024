package com.bob.student.service.impl;

import com.bob.student.domain.Student;
import com.bob.student.mapper.StudentMapper;
import com.bob.student.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 学生表 服务实现类
 * </p>
 *
 * @author Bob
 * @since 2024-11-14
 */
@Service
public class StudentServiceImp extends ServiceImpl<StudentMapper, Student> implements StudentService {

}
