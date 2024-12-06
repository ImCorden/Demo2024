package com.bob.student.mapper;

import com.bob.student.domain.StudentRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 学生角色表 Mapper 接口
 * </p>
 *
 * @author Bob
 * @since 2024-11-19
 */

public interface StudentRoleMapper extends BaseMapper<StudentRole> {


    /**
     * cleanMockData
     * <p>
     *
     * @return : boolean
     * @params : [ids]
     **/
    int cleanMockData(@Param("ids") List<Long> ids);
}
