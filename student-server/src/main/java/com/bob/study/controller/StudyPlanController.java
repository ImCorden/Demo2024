package com.bob.study.controller;

import com.bob.commontools.pojo.JsonResult;
import com.bob.commontools.pojo.vo.StudyPlanInfoVO;
import com.bob.study.domain.StudyPlan;
import com.bob.study.service.StudyPlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * <p>
 * 学习计划 前端控制器
 * </p>
 *
 * @author Bob
 * @since 2024-11-04
 */
@Tag(name = "StudyPlanController")
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/study/studyPlan")
public class StudyPlanController {

    private final StudyPlanService studyPlanService;

    /**
     * 按照Id查找计划信息
     * <p>
     * @params : [id]
     * @return : com.bob.commontools.pojo.JsonResult
     **/
    @Operation(summary = "按id查找计划详细信息")
    @GetMapping("getStudyPlanInfoById/{id}")
    public JsonResult getStudyPlanInfoById(@PathVariable("id") Long id) {
        StudyPlanInfoVO studyPlanInfoVO = new StudyPlanInfoVO();
        Optional.ofNullable(studyPlanService.getById(id))
                .ifPresent(studyPlan -> BeanUtils.copyProperties(studyPlan, studyPlanInfoVO));
        return JsonResult.ok(studyPlanInfoVO);
    }


}
