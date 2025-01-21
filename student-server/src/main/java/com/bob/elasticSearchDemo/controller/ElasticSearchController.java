package com.bob.elasticSearchDemo.controller;


import cn.hutool.core.util.ObjectUtil;
import co.elastic.clients.elasticsearch._types.aggregations.*;
import co.elastic.clients.json.JsonData;
import com.bob.commontools.pojo.JsonResult;
import com.bob.course.domain.Course;
import com.bob.course.service.CourseService;
import com.bob.elasticSearchDemo.dto.StudyPlanCourseESDto;
import com.bob.study.domain.StudyPlanCourse;
import com.bob.study.service.StudyPlanCourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregation;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregations;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilterBuilder;
import org.springframework.data.elasticsearch.core.query.HighlightQuery;
import org.springframework.data.elasticsearch.core.query.highlight.Highlight;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightField;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightParameters;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName : ElasticSearchController
 * @Description : TODO
 * @Author : Bob
 * @Date : 2025/1/13 AM11:07
 * @Version : 1.0
 **/
@RestController
@RequestMapping("/es")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ElasticSearchController {

    private final ElasticsearchTemplate elasticsearchTemplate;

    private final StudyPlanCourseService studyPlanCourseService;
    private final CourseService courseService;

    /**
     * 创建索引和数据
     * <p>
     *
     * @return : com.bob.commontools.pojo.JsonResult
     * @params : []
     **/
    @GetMapping("prepareIndexAndData")
    public JsonResult prepareIndexAndData() {
        // 创建索引
        IndexOperations indexOperations = elasticsearchTemplate.indexOps(StudyPlanCourseESDto.class);
        if (indexOperations.exists()) {
            indexOperations.delete();
        }
        indexOperations.create();
        indexOperations.putMapping();

        // 数据准备
        List<StudyPlanCourse> studyPlanCourses = studyPlanCourseService.list();
        Map<Long, List<Course>> courseGroup = courseService.list().stream().collect(Collectors.groupingBy(Course::getId));
        // 转换数据
        List<StudyPlanCourseESDto> docs = studyPlanCourses.stream().map(studyPlanCourse -> {
            StudyPlanCourseESDto data = new StudyPlanCourseESDto();
            BeanUtils.copyProperties(studyPlanCourse, data);
            data.setStudyPlanCourseId(studyPlanCourse.getId());
            Course course = courseGroup.get(studyPlanCourse.getCourseId()).get(0);
            data.setCourseInfo(course.getCourseInfo());
            data.setCourseTime(course.getCourseTime());
            return data;
        }).toList();
        elasticsearchTemplate.save(docs);
        return JsonResult.ok();
    }

    /**
     * Match 查询
     *
     * @return
     */
    @GetMapping("matchWithPage/{page}")
    public JsonResult<Object> matchWithPage(@PathVariable("page") Integer page) {
        SearchHits<StudyPlanCourseESDto> hits = elasticsearchTemplate.search(
                NativeQuery.builder()
                        .withQuery(q -> q.match(t -> t.field("studyPlanCourseType").query("A")))
                        .withPageable(PageRequest.of(page, 3))
                        .withSort(Sort.by(Sort.Order.asc("studyPlanCourseId")))
                        .build(),
                StudyPlanCourseESDto.class);
        return JsonResult.ok(getRes(hits));
    }

    /**
     * Term 查询
     *
     * @return
     */
    @GetMapping("termWithSource")
    public JsonResult<Object> termWithSource() {
        SearchHits<StudyPlanCourseESDto> hits = elasticsearchTemplate.search(
                NativeQuery.builder()
                        .withQuery(q -> q.term(t -> t.field("courseInfo").value("这个")))
                        .withSourceFilter(
                                new FetchSourceFilterBuilder()
                                        .withIncludes("courseInfo", "studyPlanCourseId")
                                        .withExcludes("courseTime")
                                        .build()
                        )
                        .build(),
                StudyPlanCourseESDto.class);
        return JsonResult.ok(getRes(hits));
    }

    /**
     * Range 查询
     *
     * @return
     */
    @GetMapping("range")
    public JsonResult<Object> range() {
        SearchHits<StudyPlanCourseESDto> hits = elasticsearchTemplate.search(
                NativeQuery.builder()
                        .withQuery(q -> q.range(r -> r.field("courseTime").gt(JsonData.of("00:20:00")).lt(JsonData.of("00:25:00"))))
                        .withSort(Sort.by(Sort.Order.asc("courseTime")))
                        .build(),
                StudyPlanCourseESDto.class);
        return JsonResult.ok(getRes(hits));
    }


    /**
     * Bool Must 查询
     *
     * @return
     */
    @GetMapping("boolMustWithSort")
    public JsonResult<Object> boolMustWithSort() {
        SearchHits<StudyPlanCourseESDto> hits = elasticsearchTemplate.search(
                NativeQuery.builder()
                        .withQuery(q -> q.bool(
                                b -> b.must(m -> m.match(t -> t.field("studyPlanCourseType").query("A")))
                                        .must(m -> m.match(t -> t.field("studyPlanId").query(1)))
                                        .mustNot(m -> m.match(t -> t.field("courseInfo").query("这个"))))
                        )
                        .withSort(Sort.by(Sort.Order.asc("studyPlanCourseId")))
                        .build(),
                StudyPlanCourseESDto.class);
        return JsonResult.ok(getRes(hits));
    }

    /**
     * Bool Should 查询
     *
     * @return
     */
    @GetMapping("boolShould")
    public JsonResult<Object> boolShould() {
        SearchHits<StudyPlanCourseESDto> hits = elasticsearchTemplate.search(
                NativeQuery.builder()
                        .withQuery(q -> q.bool(
                                b -> b.must(m -> m.match(t -> t.field("studyPlanId").query("1")))
                                        .should(s -> s.match(m -> m.field("studyPlanCourseType").query("A")))
                                        .should(s -> s.match(m -> m.field("courseTime").query("00:20:08")))
                                        .should(s -> s.match(m -> m.field("courseTime").query("00:32:00")))
                                        .minimumShouldMatch("2")) // 至少满足2个条件
                        )
                        .build(),
                StudyPlanCourseESDto.class);
        return JsonResult.ok(getRes(hits));
    }

    /**
     * Bool Should 查询
     *
     * @return
     */
    @GetMapping("highlight")
    public JsonResult<Object> highlight() {
        // 高亮参数
        HighlightParameters highlightParameters = HighlightParameters.builder().withPostTags("</em>").withPreTags("<em>").build();
        Highlight highlight = new Highlight(highlightParameters, List.of(new HighlightField("courseInfo")));
        HighlightQuery highlightQuery = new HighlightQuery(highlight, StudyPlanCourseESDto.class);

        // 查询
        SearchHits<StudyPlanCourseESDto> hits = elasticsearchTemplate.search(
                NativeQuery.builder()
                        .withQuery(q -> q.bool(
                                        b -> b.must(m -> m.match(t -> t.field("courseInfo").query("这个")))
                                )
                        )
                        .withHighlightQuery(highlightQuery)
                        .build(),
                StudyPlanCourseESDto.class);

        // 解析高亮字段
        List<StudyPlanCourseESDto> res = hits.getSearchHits().stream()
                .map(h -> {
                    StudyPlanCourseESDto content = h.getContent();
                    List<String> highlightField = h.getHighlightField("courseInfo");
                    if (ObjectUtil.isNotEmpty(highlightField)) {
                        content.setCourseInfo(highlightField.get(0));
                    }
                    return content;
                }).toList();
        return JsonResult.ok(res);
    }

    /**
     * 聚合查询
     *
     * @return
     */
    @GetMapping("aggregation")
    public JsonResult<Object> aggregation() {
        // 聚合查询
        SearchHits<StudyPlanCourseESDto> hits = elasticsearchTemplate.search(
                NativeQuery.builder()
                        .withQuery(q -> q.match(t -> t.field("studyPlanCourseType").query("A")))
                        .withAggregation("avg", Aggregation.of(a -> a.avg(avg -> avg.field("unit"))))
                        .withAggregation("sum", Aggregation.of(a -> a.sum(sum -> sum.field("unit"))))
                        .withMaxResults(0)// 不返回数据
                        .build(),
                StudyPlanCourseESDto.class);

        // 解析聚合结果
        Map<String, Object> res = new HashMap<String, Object>(2);
        List<ElasticsearchAggregation> aggregations = (List<ElasticsearchAggregation>) hits.getAggregations().aggregations();
        aggregations.forEach(agg -> {
            String name = agg.aggregation().getName();
            Object value = null;
            Aggregate aggregate = agg.aggregation().getAggregate();
            if (aggregate.isAvg()) {
                value = aggregate.avg().value();
            }
            if (aggregate.isSum()) {
                value = aggregate.sum().value();
            }
            res.put(name, value);
        });
        ElasticsearchAggregations test = (ElasticsearchAggregations) hits.getAggregations();

        return JsonResult.ok(res);
    }


    /**
     * 嵌套聚合查询
     *
     * @return
     */
    @GetMapping("termsAggregation")
    public JsonResult<Object> termsAggregation() {
        HashMap<Object, Object> res = new HashMap<>(4);
        // 聚合查询
        SearchHits<StudyPlanCourseESDto> hits = elasticsearchTemplate.search(
                NativeQuery.builder()
                        .withAggregation(
                                "courseTypeBucket",
                                Aggregation.of(
                                        a -> a.terms(t -> t.field("studyPlanCourseType"))
                                                .aggregations(Map.of(
                                                        "avg", Aggregation.of(sa -> sa.avg(avg -> avg.field("unit"))),
                                                        "max", Aggregation.of(sa -> sa.max(max -> max.field("unit"))),
                                                        "min", Aggregation.of(sa -> sa.min(min -> min.field("unit"))),
                                                        "sum", Aggregation.of(sa -> sa.sum(sum -> sum.field("unit")))
                                                ))
                                )
                        )
                        .withMaxResults(0)// 不返回数据
                        .build(),
                StudyPlanCourseESDto.class);

        ElasticsearchAggregations aggregations = (ElasticsearchAggregations) hits.getAggregations();
        Aggregate aggregate = aggregations.aggregationsAsMap().get("courseTypeBucket").aggregation().getAggregate();
        List<StringTermsBucket> buckets = aggregate.sterms().buckets().array();
        buckets.forEach(b -> {
            res.put(b.key().stringValue(),
                    Map.of(
                            "sum", b.aggregations().get("sum").sum().value(),
                            "avg", b.aggregations().get("avg").avg().value(),
                            "max", b.aggregations().get("max").max().value(),
                            "min", b.aggregations().get("min").min().value()
                    )
            );
        });


        return JsonResult.ok(res);
    }

    /**
     * 过滤桶
     *
     * @return
     */
    @GetMapping("filterBucket")
    public JsonResult<Object> filterBucket() {
        SearchHits<StudyPlanCourseESDto> hits = elasticsearchTemplate.search(
                NativeQuery.builder()
                        .withAggregation(
                                "courseTypeBucket",
                                Aggregation.of(
                                        a -> a.filter(f -> f.match(m -> m.field("studyPlanCourseType").query("A")))
                                                .aggregations(Map.of(
                                                        "avg", Aggregation.of(sa -> sa.avg(avg -> avg.field("unit"))),
                                                        "max", Aggregation.of(sa -> sa.max(max -> max.field("unit"))),
                                                        "min", Aggregation.of(sa -> sa.min(min -> min.field("unit"))),
                                                        "sum", Aggregation.of(sa -> sa.sum(sum -> sum.field("unit")))
                                                ))
                                )
                        )
                        .build()
                , StudyPlanCourseESDto.class
        );
        HashMap<Object, Object> res = new HashMap<>(4);

        ElasticsearchAggregations aggregations = (ElasticsearchAggregations) hits.getAggregations();
        Aggregate aggregate = aggregations.aggregationsAsMap().get("courseTypeBucket").aggregation().getAggregate();
        aggregate.filter().aggregations().forEach((k, v) -> {
            switch (k) {
                case "avg":
                    res.put("avg", v.avg().value());
                    break;
                case "max":
                    res.put("max", v.max().value());
                    break;
                case "min":
                    res.put("min", v.min().value());
                    break;
                case "sum":
                    res.put("sum", v.sum().value());
                    break;
            }
        });
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
























