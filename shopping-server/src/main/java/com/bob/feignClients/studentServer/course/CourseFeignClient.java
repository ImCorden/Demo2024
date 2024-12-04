package com.bob.feignClients.studentServer.course;


import com.bob.commontools.pojo.vo.CourseInfoVO;
import com.bob.feignClients.studentServer.course.fallbackFactory.CourseFallbackFactory;
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
        contextId = "course",
        path = "/course/course",
        fallbackFactory = CourseFallbackFactory.class
)
public interface CourseFeignClient {

    /**
     * 按id获取课程信息
     * @param id
     * @return
     */
    @GetMapping(value = "getCourseInfoById/{id}",headers = "Content-Type=application/json;charset=UTF-8")
    CourseInfoVO getCourseInfoById(@PathVariable("id") Long id);
}
