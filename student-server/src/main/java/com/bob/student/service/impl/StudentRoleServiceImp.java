package com.bob.student.service.impl;

import com.bob.student.domain.StudentRole;
import com.bob.student.mapper.StudentRoleMapper;
import com.bob.student.service.StudentRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 学生角色表 服务实现类
 * </p>
 *
 * @author Bob
 * @since 2024-11-19
 */
@Service
public class StudentRoleServiceImp extends ServiceImpl<StudentRoleMapper, StudentRole> implements StudentRoleService {

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
}
