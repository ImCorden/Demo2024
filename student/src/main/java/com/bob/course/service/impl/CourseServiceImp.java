package com.bob.course.service.impl;

import com.bob.course.domain.Course;
import com.bob.course.mapper.CourseMapper;
import com.bob.course.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程表 服务实现类
 * </p>
 *
 * @author Bob
 * @since 2024-11-04
 */
@Service
public class CourseServiceImp extends ServiceImpl<CourseMapper, Course> implements CourseService {

}
