package com.bob.student.service;

import com.bob.student.domain.StudentRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 学生角色表 服务类
 * </p>
 *
 * @author Bob
 * @since 2024-11-19
 */
public interface StudentRoleService extends IService<StudentRole> {

    /**
     * 保存默认学生角色
     * <p>
     * @params : [id]
     * @return : boolean
     **/
    boolean saveDefaultStudentRole(Long studentId);

    /**
     * 清理mock数据
     * @param ids
     * @return
     */
    boolean cleanMockData(List<Long> ids);
}
