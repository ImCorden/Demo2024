package com.bob.student.mapper;

import com.bob.student.domain.StudentRegistration;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 学生报名资格 Mapper 接口
 * </p>
 *
 * @author Bob
 * @since 2024-11-04
 */
public interface StudentRegistrationMapper extends BaseMapper<StudentRegistration> {


    /**
     * 清理mock数据
     * @param ids
     * @return
     */
    boolean cleanMockData(@Param("ids") List<Long> ids);
}
