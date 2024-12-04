package com.bob.study.controller;



import com.bob.commontools.pojo.JsonResult;
import com.bob.commontools.pojo.vo.StudyPlanCourseInfoVO;
import com.bob.commontools.pojo.bo.StudyPlanCourseSelectBO;
import com.bob.study.service.StudyPlanCourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * <p>
 * 学习计划课程 前端控制器
 * </p>
 *
 * @author Bob
 * @since 2024-11-04
 */
@Tag(name = "studyPlanCourseController")
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@RequestMapping("/study/studyPlanCourse")
public class StudyPlanCourseController {

    private final StudyPlanCourseService studyPlanCourseService;

    /**
     * 向购物车加商品
     * <p>
     *
     * @return : com.bob.commontools.pojo.JsonResult
     * @params : [studyPlanCourseSelectBO]
     **/
    @Operation(summary = "Feign调用添加购物车")
    @PostMapping("addToCart")
    public JsonResult addToCart(@RequestBody StudyPlanCourseSelectBO studyPlanCourseSelectBO) {
        boolean res = studyPlanCourseService.findCourseInfoAndAddToCart(studyPlanCourseSelectBO);
        if (res) {
            return JsonResult.ok();
        }
        return JsonResult.errorMsg("保存失败");
    }

    /**
     * 按id查找课程详细信息
     * <p>
     *
     * @return : com.bob.commontools.pojo.JsonResult
     * @params : [id]
     **/
    @Operation(summary = "按id查找课程详细信息")
    @GetMapping("getStudyPlanCourseInfoById/{id}")
    public JsonResult getStudyPlanCourseInfoById(@PathVariable Long id) {
        StudyPlanCourseInfoVO studyPlanCourseInfoVO = new StudyPlanCourseInfoVO();
        Optional.ofNullable(studyPlanCourseService.getById(id))
                .ifPresent(course -> BeanUtils.copyProperties(course, studyPlanCourseInfoVO));
        return JsonResult.ok(studyPlanCourseInfoVO);
    }

}
