package com.bob.course.vo;


import com.bob.course.domain.Course;
import lombok.*;

import java.io.Serializable;

/**
 * @ClassName : PageCourse
 * @Description : TODO
 *
 *
 * @Author : Bob
 * @Date : 2024/11/4 21:15
 * @Version : 1.0
 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PageCourseVO extends Course implements Serializable {

    /**
     * 总数
     */
    private long total = 0;
    /**
     * 每页显示条数，默认 10
     */
    private long size = 10;

    /**
     * 当前页
     */
    private long current = 1;
}
