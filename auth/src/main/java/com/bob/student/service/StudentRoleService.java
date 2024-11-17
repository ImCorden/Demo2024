package com.bob.student.service;

import com.bob.commontools.exception.BusinessException;
import com.bob.student.domain.StudentRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 学生角色表 服务类
 * </p>
 *
 * @author Bob
 * @since 2024-11-16
 */
public interface StudentRoleService extends IService<StudentRole> {

    /**
     * 通过StudentID获取学生角色
     * <p>
     * @params : [id]
     * @return : java.util.List<java.lang.Integer>
     **/
    List<Long> getRoleIdsByStudentId(Long id) throws BusinessException;
}
