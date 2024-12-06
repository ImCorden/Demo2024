package com.bob.student.mapper;

import com.bob.student.domain.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 学生表 Mapper 接口
 * </p>
 *
 * @author Bob
 * @since 2024-11-14
 */
public interface StudentMapper extends BaseMapper<Student> {

    /**
     * 清理mock数据
     * <p>
     * @params : [ids]
     * @return : boolean
     **/
    boolean cleanMockData(@Param("ids") List<Long> ids);
}
