package com.bob.student.service.impl;

import com.bob.student.domain.StudentCourse;
import com.bob.student.mapper.StudentCourseMapper;
import com.bob.student.service.StudentCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 学生课程 服务实现类
 * </p>
 *
 * @author Bob
 * @since 2024-11-01
 */
@Service
public class StudentCourseServiceImp extends ServiceImpl<StudentCourseMapper, StudentCourse> implements StudentCourseService {

}
