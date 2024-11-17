package com.bob.student.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bob.commontools.exception.BusinessException;
import com.bob.student.domain.StudentRole;
import com.bob.student.mapper.StudentRoleMapper;
import com.bob.student.service.StudentRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 学生角色表 服务实现类
 * </p>
 *
 * @author Bob
 * @since 2024-11-16
 */
@Service
public class StudentRoleServiceImp extends ServiceImpl<StudentRoleMapper, StudentRole> implements StudentRoleService {

    /**
     * 通过StudentID获取学生角色
     * <p>
     *
     * @return : java.util.List<java.lang.Integer>
     * @params : [id]
     **/
    @Override
    public List<Long> getRoleIdsByStudentId(Long id) throws BusinessException {

        if (ObjectUtil.isNull(id)) {
            throw new BusinessException("学生ID不能为空！");
        }
        return this.listObjs(
                new LambdaQueryWrapper<StudentRole>()
                        .select(StudentRole::getRoleId)
                        .eq(StudentRole::getStudentId, id));
    }
}
