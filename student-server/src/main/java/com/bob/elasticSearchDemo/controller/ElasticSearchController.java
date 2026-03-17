package com.bob.elasticSearchDemo.controller;


import cn.hutool.core.util.ObjectUtil;
import co.elastic.clients.elasticsearch._types.aggregations.*;
import co.elastic.clients.json.JsonData;
import com.bob.commontools.exception.BizException;
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
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilterBuilder;
import org.springframework.data.elasticsearch.core.query.HighlightQuery;
import org.springframework.data.elasticsearch.core.query.highlight.Highlight;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightField;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightParameters;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;


import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

    private final ElasticsearchOperations elasticsearchOperations;

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
        IndexOperations indexOperations = elasticsearchOperations.indexOps(StudyPlanCourseESDto.class);
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
        elasticsearchOperations.save(docs);
        return JsonResult.ok();
    }

    /**
     * Match 查询-StudyPlanCourseType
     *
     * @return
     */
    @GetMapping("matchStudyPlanCourseTypeWithPage")
    public JsonResult<Object> matchWithPage(@RequestParam("courseType") String studyPlanCourseType,
                                            @RequestParam("page") Integer page,
                                            @RequestParam("size") Integer size) {
        SearchHits<StudyPlanCourseESDto> hits = elasticsearchOperations.search(
                NativeQuery.builder()
                        .withQuery(q -> q.match(t -> t.field("studyPlanCourseType").query(studyPlanCourseType)))
                        .withPageable(PageRequest.of(page, size))
                        .withSort(Sort.by(Sort.Order.asc("studyPlanCourseId")))
                        .build(),
                StudyPlanCourseESDto.class);
        return JsonResult.ok(getRes(hits));
    }

    /**
     * Term 查询-CourseName
     *
     * @return
     */
    @GetMapping("termCourseNameWithSource")
    public JsonResult<Object> termWithSource(@RequestParam("courseName") String courseName) {
        SearchHits<StudyPlanCourseESDto> hits = elasticsearchOperations.search(
                NativeQuery.builder()
                        .withQuery(q -> q.term(t -> t.field("courseName").value(courseName)))
                        .withSourceFilter(
                                new FetchSourceFilterBuilder()
                                        .withIncludes("courseName", "studyPlanCourseId")
                                        .withExcludes("courseTime")
                                        .build()
                        )
                        .build(),
                StudyPlanCourseESDto.class);
        return JsonResult.ok(getRes(hits));
    }

    /**
     * Range 查询-courseTime
     *
     * @return
     */
    @GetMapping("rangeByCourseTime")
    public JsonResult<Object> range(@RequestParam("startTime") @DateTimeFormat(pattern = "HH:mm:ss") LocalTime startTime,
                                    @RequestParam("startTime") @DateTimeFormat(pattern = "HH:mm:ss") LocalTime endTime) throws BizException {
        // 参数校验，格式化
        if (ObjectUtil.isEmpty(startTime) || ObjectUtil.isEmpty(endTime)) {
            throw new BizException("起始或结束时间为空！");
        }
        String start = startTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        String end = endTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        SearchHits<StudyPlanCourseESDto> hits = elasticsearchOperations.search(
                NativeQuery.builder()
                        .withQuery(q -> q.range(r -> r.field("courseTime").gt(JsonData.of(start)).lt(JsonData.of(end))))
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
        SearchHits<StudyPlanCourseESDto> hits = elasticsearchOperations.search(
                NativeQuery.builder()
                        .withQuery(q -> q.bool(
                                b -> b.must(m -> m.match(t -> t.field("studyPlanCourseType").query("A")))
                                        .must(m -> m.match(t -> t.field("studyPlanId").query(1)))
                                        .mustNot(m -> m.match(t -> t.field("courseInfo").query("系统"))))
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
        SearchHits<StudyPlanCourseESDto> hits = elasticsearchOperations.search(
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
    public JsonResult<Object> highlight(@RequestParam String courseInfo) {
        // 高亮参数
        HighlightParameters highlightParameters = HighlightParameters.builder().withPostTags("</em>").withPreTags("<em>").withNumberOfFragments(0).build();
        Highlight highlight = new Highlight(highlightParameters, List.of(new HighlightField("courseInfo")));
        HighlightQuery highlightQuery = new HighlightQuery(highlight, StudyPlanCourseESDto.class);

        // 查询
        SearchHits<StudyPlanCourseESDto> hits = elasticsearchOperations.search(
                NativeQuery.builder()
                        .withQuery(q -> q.bool(
                                        b -> b.must(m -> m.match(t -> t.field("courseInfo").query(courseInfo)))
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
                        // 因为.withNumberOfFragments(0)不进行分片，所以不需要拼接，直接去第一个就行
                        // StringBuilder sb = new StringBuilder();
                        // highlightField.forEach(sb::append);
                        // content.setCourseInfo(sb.toString());
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
        SearchHits<StudyPlanCourseESDto> hits = elasticsearchOperations.search(
                NativeQuery.builder()
                        .withQuery(q -> q.match(t -> t.field("studyPlanCourseType").query("A")))
                        .withAggregation("avg", Aggregation.of(a -> a.avg(avg -> avg.field("unit"))))
                        .withAggregation("sum", Aggregation.of(a -> a.sum(sum -> sum.field("unit"))))
                        .withMaxResults(0)// 不返回数据
                        .build(),
                StudyPlanCourseESDto.class);

        // 解析聚合结果
        Map<String, Object> res = new HashMap<String, Object>(2);
        ElasticsearchAggregations aggregations = (ElasticsearchAggregations) hits.getAggregations();
        ElasticsearchAggregation avgAgg = aggregations.get("avg");
        if (avgAgg != null) {
            // 链式调用获取值：Aggregation -> Aggregate -> Avg -> value
            res.put("avg", avgAgg.aggregation().getAggregate().avg().value());
        }

        ElasticsearchAggregation sumAgg = aggregations.get("sum");
        if (sumAgg != null) {
            res.put("sum", sumAgg.aggregation().getAggregate().sum().value());
        }
        return JsonResult.ok(res);
    }


    /**
     * 嵌套聚合查询
     *
     * @return
     */
    @GetMapping("termsAggregation")
    public JsonResult<Object> termsAggregation() {
        // HashMap<Object, Object> res = new HashMap<>(4);
        Map<String, Map<String, Double>> res = new HashMap<>();
        // 聚合查询
        SearchHits<StudyPlanCourseESDto> hits = elasticsearchOperations.search(
                NativeQuery.builder()
                        .withAggregation(
                                "courseTypeBucket",
                                Aggregation.of(
                                        a -> a.terms(t -> t.field("studyPlanCourseType"))
                                                // .aggregations(Map.of(
                                                //         "avg", Aggregation.of(sa -> sa.avg(avg -> avg.field("unit"))),
                                                //         "max", Aggregation.of(sa -> sa.max(max -> max.field("unit"))),
                                                //         "min", Aggregation.of(sa -> sa.min(min -> min.field("unit"))),
                                                //         "sum", Aggregation.of(sa -> sa.sum(sum -> sum.field("unit")))
                                                // ))

                                                .aggregations("avg", sub -> sub.avg(avg -> avg.field("unit")))
                                                .aggregations("max", sub -> sub.max(max -> max.field("unit")))
                                                .aggregations("min", sub -> sub.min(min -> min.field("unit")))
                                                .aggregations("sum", sub -> sub.sum(sum -> sum.field("unit")))
                                )
                        )
                        .withMaxResults(0)// 不返回数据
                        .build(),
                StudyPlanCourseESDto.class);

        // ElasticsearchAggregations aggregations = (ElasticsearchAggregations) hits.getAggregations();
        // Aggregate aggregate = aggregations.aggregationsAsMap().get("courseTypeBucket").aggregation().getAggregate();
        // List<StringTermsBucket> buckets = aggregate.sterms().buckets().array();
        // buckets.forEach(b -> {
        //     res.put(b.key().stringValue(),
        //             Map.of(
        //                     "sum", b.aggregations().get("sum").sum().value(),
        //                     "avg", b.aggregations().get("avg").avg().value(),
        //                     "max", b.aggregations().get("max").max().value(),
        //                     "min", b.aggregations().get("min").min().value()
        //             )
        //     );
        // });
        if (hits.hasAggregations()) {
            // 【优化点 2】拆解深层嵌套，增加可读性和空指针防御
            ElasticsearchAggregations esAggs = (ElasticsearchAggregations) hits.getAggregations();
            

            // 安全获取 bucket 聚合
            // 注意：这里的 get("name") 返回的是 Aggregate 对象
            Aggregate bucketAgg = esAggs.get("courseTypeBucket").aggregation().getAggregate();

            // 只有当聚合类型真的是 StringTerms 时才处理
            if (bucketAgg.isSterms()) {
                List<StringTermsBucket> buckets = bucketAgg.sterms().buckets().array();

                // 【优化点 3】使用 Stream 流式处理，代码更优雅
                res = buckets.stream().collect(Collectors.toMap(
                        // Key: bucket 的 key
                        b -> b.key().stringValue(),
                        // Value: 提取内部的子聚合结果
                        b -> {
                            Map<String, Aggregate> subAggs = b.aggregations();
                            // 使用 Map.of 生成不可变Map (注意：数值不能为 null)
                            return Map.of(
                                    "sum", subAggs.get("sum").sum().value(),
                                    "avg", subAggs.get("avg").avg().value(),
                                    "max", subAggs.get("max").max().value(),
                                    "min", subAggs.get("min").min().value()
                            );
                        },
                        // MergeFunction: 如果key冲突(理论上不会)，取新值
                        (v1, v2) -> v2
                ));
            }
        }
        return JsonResult.ok(res);
    }

    /**
     * 过滤桶
     *
     * @return
     */
    @GetMapping("filterBucket")
    public JsonResult<Object> filter(@RequestParam String studyPlanCourseType) {
        SearchHits<StudyPlanCourseESDto> hits = elasticsearchOperations.search(
                NativeQuery.builder()
                        .withMaxResults(0)
                        .withAggregation("courseTypeBucket", Aggregation.of(a -> a
                                .filter(f -> f
                                        .match(t -> t.field("studyPlanCourseType").query(studyPlanCourseType))
                                )
                                // 在 filter 内部挂载子聚合 (链式调用，比 Map.of 更直观)
                                .aggregations("avg_unit", sub -> sub.avg(avg -> avg.field("unit")))
                                .aggregations("max_unit", sub -> sub.max(max -> max.field("unit")))
                                .aggregations("min_unit", sub -> sub.min(min -> min.field("unit")))
                                .aggregations("sum_unit", sub -> sub.sum(sum -> sum.field("unit")))
                        ))
                        .build(),
                StudyPlanCourseESDto.class);
        HashMap<Object, Object> res = new HashMap<>(4);
        // 判断是否是ElasticsearchAggregations，是的话强转并赋值（Java16新特性）
        if (hits.getAggregations() instanceof ElasticsearchAggregations aggregations) {
            // 获取外层的 Filter 聚合
            ElasticsearchAggregation bucketEsAgg = aggregations.get("courseTypeBucket");
            if (bucketEsAgg != null) {
                // 获取底层的 ES 原生 Aggregate 对象
                Aggregate filterAggregate = bucketEsAgg.aggregation().getAggregate();
                // 确保它是 Filter 类型 (虽然在 Query 里定义了，但在 Java 中最好做类型检查)
                if (filterAggregate.isFilter()) {
                    // 获取 Filter 内部的子聚合 Map
                    Map<String, Aggregate> subAggMap = filterAggregate.filter().aggregations();
                    // 逐个提取子聚合数值
                    res.put("avg", subAggMap.get("avg_unit").avg().value());
                    res.put("max", subAggMap.get("max_unit").max().value());
                    res.put("min", subAggMap.get("min_unit").min().value());
                    res.put("sum", subAggMap.get("sum_unit").sum().value());
                }
            }
        }
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