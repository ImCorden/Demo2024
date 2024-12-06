package com.bob.student.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.bob.student.domain.Student;
import com.bob.student.mapper.StudentMapper;
import com.bob.student.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 学生表 服务实现类
 * </p>
 *
 * @author Bob
 * @since 2024-11-04
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StudentServiceImp extends ServiceImpl<StudentMapper, Student> implements StudentService {

    private final StudentMapper studentMapper;

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
        return studentMapper.cleanMockData(ids);
    }
}
