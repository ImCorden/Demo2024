package com.bob.elasticSearchDemo.Repository;


import com.bob.elasticSearchDemo.dto.StudyPlanCourseESDto;
import org.springframework.data.elasticsearch.annotations.Highlight;
import org.springframework.data.elasticsearch.annotations.HighlightField;
import org.springframework.data.elasticsearch.annotations.HighlightParameters;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.repository.Repository;

/**
 * @ClassName : ElasticSearchRepository
 * @Description : TODO
 * @Author : Bob
 * @Date : 2025/1/21 PM1:08
 * @Version : 1.0
 **/
public interface ElasticSearchRepository extends Repository<StudyPlanCourseESDto, Long> {

    SearchHits<StudyPlanCourseESDto> findByStudyPlanCourseType(String studyPlanCourseType);

    @Highlight(
            fields = @HighlightField(
                    name = "courseInfo",
                    parameters = @HighlightParameters(preTags = "<em>", postTags = "</em>")
            )
    )
    SearchHits<StudyPlanCourseESDto> searchByCourseInfo(String infoHighLight);


}
