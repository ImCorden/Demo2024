package com.bob.student.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.bob.student.domain.StudentRole;
import com.bob.student.mapper.StudentRoleMapper;
import com.bob.student.service.StudentRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 学生角色表 服务实现类
 * </p>
 *
 * @author Bob
 * @since 2024-11-19
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StudentRoleServiceImp extends ServiceImpl<StudentRoleMapper, StudentRole> implements StudentRoleService {

    private final StudentRoleMapper studentRoleMapper;

    /**
     * 保存默认学生角色
     * <p>
     *
     * @return : boolean
     * @params : [id]
     **/
    @Override
    public boolean saveDefaultStudentRole(Long studentId) {
        return this.save(StudentRole.builder()
                .studentId(studentId)
                .roleId(1L)
                .build());
    }

    /**
     * 清理mock数据
     * <p>
     * @params : [ids]
     * @return : boolean
     **/
    @Override
    public boolean cleanMockData(List<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return false;
        }
        return studentRoleMapper.cleanMockData(ids) > 0;
    }
}
