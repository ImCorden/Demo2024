package com.bob.feignClients.studentServer.course.fallbackFactory;



import com.bob.commontools.pojo.vo.CourseInfoVO;
import com.bob.feignClients.studentServer.course.CourseFeignClient;
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
public class CourseFallbackFactory implements FallbackFactory<CourseFeignClient> {


    @Override
    public CourseFeignClient create(Throwable cause) {
        return new CourseFeignClient() {

            @Override
            public CourseInfoVO getCourseInfoById(Long id) {
                log.error("##FALLBACK FACTORY : CartItemFeignClient FallBack----------------------");
                log.error("##FALLBACK FACTORY : {}", String.valueOf(cause));
                log.error("##FALLBACK FACTORY : {}", cause.getMessage());
                return null;
            }
        };
    }
}
