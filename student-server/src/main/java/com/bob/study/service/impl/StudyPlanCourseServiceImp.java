package com.bob.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bob.aspect.StudentHolder;
import com.bob.course.domain.Course;
import com.bob.course.service.CourseService;
import com.bob.commontools.pojo.bo.StudyPlanCourseCartItemBO;
import com.bob.feignClients.shoppingServer.cart.CartItemFeignClient;
import com.bob.commontools.pojo.bo.StudyPlanCourseSelectBO;
import com.bob.study.domain.StudyPlanCourse;
import com.bob.study.mapper.StudyPlanCourseMapper;
import com.bob.study.service.StudyPlanCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 学习计划课程 服务实现类
 * </p>
 *
 * @author Bob
 * @since 2024-11-04
 */
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StudyPlanCourseServiceImp extends ServiceImpl<StudyPlanCourseMapper, StudyPlanCourse> implements StudyPlanCourseService {

    private final CourseService courseService;

    private final CartItemFeignClient cartItemFeignClient;

    /**
     * 查询课程信息并添加购物车
     * <p>
     *
     * @return : boolean
     * @params : [studyPlanCourseSelectBO]
     **/
    @Override
    public boolean findCourseInfoAndAddToCart(StudyPlanCourseSelectBO studyPlanCourseSelectBO) {

        // Long studentId = studyPlanCourseSelectBO.getStudentId();
        Long studentId = StudentHolder.getId();
        // 计划课程信息
        List<StudyPlanCourse> studyPlanCourseList = this.list(
                new LambdaQueryWrapper<StudyPlanCourse>()
                        .select(StudyPlanCourse::getId, StudyPlanCourse::getCourseId, StudyPlanCourse::getPrice)
                        .in(StudyPlanCourse::getId, studyPlanCourseSelectBO.getStudyPlanCourseIds())
        );
        // 课程信息
        Map<Long, Course> courseMap = courseService.list(
                        new LambdaQueryWrapper<Course>()
                                .select(Course::getId, Course::getCourseImg, Course::getCourseName)
                                .in(Course::getId, studyPlanCourseList.stream().map(StudyPlanCourse::getCourseId).toList()))
                .stream()
                .collect(Collectors.toMap(Course::getId, c -> c));
        // 组装发送信息
        List<StudyPlanCourseCartItemBO> sendMsg = studyPlanCourseList.stream()
                .map(studyPlanCourse -> {
                    Long courseId = studyPlanCourse.getCourseId();
                    return StudyPlanCourseCartItemBO.builder()
                            .studentId(studentId)
                            .itemType("COURSE")
                            .itemPrice(studyPlanCourse.getPrice())
                            .itemId(courseId)
                            .itemName(courseMap.get(courseId).getCourseName())
                            .itemPicUrl(courseMap.get(courseId).getCourseImg())
                            .build();
                }).toList();
        return cartItemFeignClient.addCartItem(sendMsg);
    }
}
