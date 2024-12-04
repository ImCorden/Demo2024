package com.bob.study.service;

import com.bob.commontools.pojo.bo.StudyPlanCourseSelectBO;
import com.bob.study.domain.StudyPlanCourse;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 学习计划课程 服务类
 * </p>
 *
 * @author Bob
 * @since 2024-11-04
 */
public interface StudyPlanCourseService extends IService<StudyPlanCourse> {

    /**
     * 查询课程信息并添加购物车
     * <p>
     * @params : [studyPlanCourseSelectBO]
     * @return : boolean
     **/
    boolean findCourseInfoAndAddToCart(StudyPlanCourseSelectBO studyPlanCourseSelectBO);
}
