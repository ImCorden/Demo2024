package com.bob.feignClients.studentServer.studyPlanCourse.fallbackFactory;



import com.bob.commontools.pojo.vo.StudyPlanCourseInfoVO;
import com.bob.feignClients.studentServer.studyPlanCourse.StudyPlanCourseFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;


/**
 * @ClassName : CartItemFeignClientFallbackFactory
 * @Description : TODO
 * @Author : Bob
 * @Date : 2024/11/9 21:57
 * @Version : 1.0
 **/
@Component
@Slf4j
public class StudyPlanCourseFallbackFactory implements FallbackFactory<StudyPlanCourseFeignClient> {


    @Override
    public StudyPlanCourseFeignClient create(Throwable cause) {
        return new StudyPlanCourseFeignClient() {

            @Override
            public StudyPlanCourseInfoVO getStudyPlanCourseInfoById(Long id) {
                log.error("##FALLBACK FACTORY : StudyPlanCourse Fallback----------------------");
                log.error("##FALLBACK FACTORY : {}", String.valueOf(cause));
                log.error("##FALLBACK FACTORY : {}", cause.getMessage());
                return null;
            }
        };
    }
}
