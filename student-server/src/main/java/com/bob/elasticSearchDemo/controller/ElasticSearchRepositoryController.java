package com.bob.elasticSearchDemo.controller;


import cn.hutool.core.util.ObjectUtil;
import com.bob.commontools.pojo.JsonResult;
import com.bob.elasticSearchDemo.Repository.ElasticSearchRepository;
import com.bob.elasticSearchDemo.dto.StudyPlanCourseESDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName : ElasticSearchController
 * @Description : TODO
 * @Author : Bob
 * @Date : 2025/1/13 AM11:07
 * @Version : 1.0
 **/
@RestController
@RequestMapping("/esRepository")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ElasticSearchRepositoryController {

    private final ElasticSearchRepository elasticSearchRepository;


    /**
     * 按课程类型查询
     * <p>
     * @params : [type]
     * @return : com.bob.commontools.pojo.JsonResult
     **/
    @GetMapping("findByStudyPlanCourseType/{type}")
    public JsonResult findByStudyPlanCourseType(@PathVariable("type") String type) {
        SearchHits<StudyPlanCourseESDto> hits = elasticSearchRepository.findByStudyPlanCourseType(type);
        return JsonResult.ok(getRes(hits));
    }

    /**
     * 高亮查询
     * @return
     */
    @GetMapping("findByCourseInfoHighLight")
    public JsonResult findByCourseInfoHighLight() {
        SearchHits<StudyPlanCourseESDto> hits = elasticSearchRepository.searchByCourseInfo("这个");
        // 提取Highlight
        List<StudyPlanCourseESDto> res = hits.getSearchHits().stream()
                .map(h -> {
                    StudyPlanCourseESDto dto = h.getContent();
                    List<String> highlightField = h.getHighlightField("courseInfo");
                    if (ObjectUtil.isNotEmpty(highlightField)) {
                        dto.setCourseInfo(highlightField.get(0));
                    }
                    return dto;
                }).toList();

        return JsonResult.ok(res);
    }



    /**
     * 解析
     * <p>
     *
     * @return : com.bob.commontools.pojo.JsonResult<java.lang.Object>
     * @params : []
     **/
    private Object getRes(SearchHits<StudyPlanCourseESDto> hits) {
        return hits.getSearchHits().stream().map(SearchHit::getContent).toList();
    }

}
























