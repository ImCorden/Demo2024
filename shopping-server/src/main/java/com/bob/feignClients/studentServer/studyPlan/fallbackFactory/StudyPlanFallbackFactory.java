package com.bob.feignClients.studentServer.studyPlan.fallbackFactory;


import com.bob.commontools.pojo.vo.StudyPlanInfoVO;
import com.bob.feignClients.studentServer.studyPlan.StudyPlanFeignClient;
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
public class StudyPlanFallbackFactory implements FallbackFactory<StudyPlanFeignClient> {


    @Override
    public StudyPlanFeignClient create(Throwable cause) {
        return new StudyPlanFeignClient() {

            @Override
            public StudyPlanInfoVO getStudyPlanInfoById(Long id) {
                log.error("##FALLBACK FACTORY : StudyPlan Fallback----------------------");
                log.error("##FALLBACK FACTORY : {}", String.valueOf(cause));
                log.error("##FALLBACK FACTORY : {}", cause.getMessage());
                return null;
            }
        };
    }
}
