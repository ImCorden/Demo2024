package com.bob.student.service;

import com.bob.student.domain.Student;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 学生表 服务类
 * </p>
 *
 * @author Bob
 * @since 2024-11-04
 */
public interface StudentService extends IService<Student> {


    /**
     * 清理mock数据
     * <p>
     * @params : [ids]
     * @return : boolean
     **/
    boolean cleanMockData(List<Long> ids);
}
