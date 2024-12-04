package com.bob.feignClients.studentServer.studyPlan;


import com.bob.commontools.pojo.vo.StudyPlanInfoVO;
import com.bob.feignClients.studentServer.studyPlan.fallbackFactory.StudyPlanFallbackFactory;
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
        contextId = "studyPlan",
        path = "/study/studyPlan",
        fallbackFactory = StudyPlanFallbackFactory.class
)
public interface StudyPlanFeignClient {

    /**
     * 按id获取课程信息
     * @param id
     * @return
     */
    @GetMapping(value = "getStudyPlanInfoById/{id}",headers = "Content-Type=application/json;charset=UTF-8")
    StudyPlanInfoVO getStudyPlanInfoById(@PathVariable("id") Long id);
}
