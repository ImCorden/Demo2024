package com.bob.feignClients.studentServer.studyPlanCourse;


import com.bob.commontools.pojo.vo.StudyPlanCourseInfoVO;
import com.bob.feignClients.studentServer.studyPlanCourse.fallbackFactory.StudyPlanCourseFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @InterfaceName : CourseFeignClient
 * @Description : TODO
 * @Author : Bob
 * @Date : 2024/12/2 22:02
 * @Version : 1.0
 **/
@FeignClient(name = "student-server",
        contextId = "studyPlanCourse",
        path = "/study/studyPlanCourse",
        fallbackFactory = StudyPlanCourseFallbackFactory.class
)
public interface StudyPlanCourseFeignClient {

    /**
     * 按id获取课程信息
     * @param id
     * @return
     */
    @GetMapping(value = "getStudyPlanCourseInfoById/{id}",headers = "Content-Type=application/json;charset=UTF-8")
    StudyPlanCourseInfoVO getStudyPlanCourseInfoById(@PathVariable("id") Long id);
}
