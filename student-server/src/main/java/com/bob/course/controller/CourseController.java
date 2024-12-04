package com.bob.course.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bob.commontools.pojo.JsonResult;
import com.bob.commontools.pojo.vo.CourseInfoVO;
import com.bob.course.domain.Course;
import com.bob.course.service.CourseService;
import com.bob.course.vo.PageCourseVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * <p>
 * 课程表 前端控制器
 * </p>
 *
 * @author Bob
 * @since 2024-11-04
 */
@Tag(name = "Course Controller")
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/course/course")
public class CourseController {

    private final CourseService courseService;

    /**
     * 分页
     * <p>
     *
     * @return : com.bob.commontools.pojo.JsonResult
     * @params : [pageCourseVO]
     **/
    @Operation(summary = "分页List")
    @PostMapping("listByPage")
    public JsonResult listCourse(@RequestBody PageCourseVO pageCourseVO) {
        Page<Course> page = courseService.page(
                Page.of(
                        pageCourseVO.getCurrent(),
                        pageCourseVO.getSize()
                )
        );
        return JsonResult.ok(page);
    }

    /**
     * 按id查找课程详细信息
     * <p>
     *
     * @return : com.bob.commontools.pojo.JsonResult
     * @params : [id]
     **/
    @Operation(summary = "按id查找课程详细信息")
    @GetMapping("getCourseInfoById/{id}")
    public JsonResult getCourseInfoById(@PathVariable Long id) {
        CourseInfoVO courseInfoVO = new CourseInfoVO();
        Optional.ofNullable(courseService.getById(id))
                .ifPresent(course -> BeanUtils.copyProperties(course, courseInfoVO));
        return JsonResult.ok(courseInfoVO);
    }
}
