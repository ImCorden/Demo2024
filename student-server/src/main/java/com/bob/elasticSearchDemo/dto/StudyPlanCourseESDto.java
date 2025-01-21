package com.bob.elasticSearchDemo.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalTime;

/**
 * <p>
 * 学习计划课程
 * </p>
 *
 * @author Bob
 * @since 2024-11-04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(indexName = "study_plan_course", createIndex = true)
@Setting(shards = 3, replicas = 1)
public class StudyPlanCourseESDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Field(type = FieldType.Long, index = true)
    private Long id;

    /**
     * 学习计划课程id(用于排序，实际同id)
     */
    private Long studyPlanCourseId;

    /**
     * 学习计划id
     */
    @Field(type = FieldType.Long)
    private Long studyPlanId;

    /**
     * 课程id
     */
    @Field(type = FieldType.Long)
    private Long courseId;

    /**
     * 课程名字
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String courseName;

    /**
     * 课程缩略图
     */
    @Field(type = FieldType.Text)
    private String courseImg;

    /**
     * 学分
     */
    @Field(type = FieldType.Double)
    private BigDecimal unit;

    /**
     * 课程类别(A.B.C.D)
     */
    @Field(type = FieldType.Keyword)
    private String studyPlanCourseType;

    /**
     * 考试id
     */
    @Field(type = FieldType.Long)
    private Long examId;

    /**
     * 单价
     */
    @Field(type = FieldType.Double)
    private BigDecimal price;

    /**
     * 课程简介（补充）
     */
    @Field(type=FieldType.Text, analyzer = "ik_max_word")
    private String courseInfo;

    /**
     * 课程时长(补充)
     */
    @Field(type = FieldType.Date,format = DateFormat.hour_minute_second)
    private LocalTime courseTime;
}
