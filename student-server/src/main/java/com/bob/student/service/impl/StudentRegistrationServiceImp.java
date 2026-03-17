package com.bob.student.service.impl;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bob.commontools.exception.BizException;
import com.bob.commontools.pojo.enums.YesOrNo;
import com.bob.commontools.utils.GsonUtils;
import com.bob.commontools.utils.RedisUtil;
import com.bob.core.pojo.Constant;
import com.bob.student.domain.Student;
import com.bob.student.service.StudentRoleService;
import com.bob.student.service.StudentService;
import com.bob.commontools.pojo.bo.StudentRegistrationProvinceBO;
import com.bob.student.domain.StudentRegistration;
import com.bob.student.mapper.StudentRegistrationMapper;
import com.bob.student.service.StudentRegistrationService;
import com.bob.stream.StreamProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 学生报名资格 服务实现类
 * </p>
 *
 * @author Bob
 * @since 2024-11-04
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class StudentRegistrationServiceImp extends ServiceImpl<StudentRegistrationMapper, StudentRegistration> implements StudentRegistrationService {

    private final StreamProducer streamProducer;
    private final StudentService studentService;
    private final StudentRoleService studentRoleService;
    private final RedisUtil redisUtil;

    private final StudentRegistrationMapper studentRegistrationMapper;

    /**
     * 校验是否可以报名，可以报名，发送mq消息
     * 废弃，性能很差，在高并发场景下，会打爆数据库
     * <p>
     *
     * @return : boolean
     * @params : [studentRegistrationProvinceBO]
     **/
    @Override
    public boolean checkByIdentityCode(StudentRegistrationProvinceBO studentRegistrationProvinceBO) {

        // 查询是否已经报名过
        List<StudentRegistration> res = this.list(new LambdaQueryWrapper<StudentRegistration>()
                .eq(StudentRegistration::getIdentityCode, studentRegistrationProvinceBO.getIdentityCode())
                .eq(StudentRegistration::getYear, studentRegistrationProvinceBO.getYear())
        );
        // 不存在发送Msg
        if (res.isEmpty()) {
            return streamProducer.sendSyncSingleMsg(GsonUtils.object2Json(studentRegistrationProvinceBO));
        }
        return false;
    }

    /**
     * 直接报名
     * <p>
     *
     * @return : boolean
     * @params : [studentRegistrationProvinceBO]
     **/
    @Override
    @SentinelResource(value = "sendRegistrationMsg", fallback = "sendRegistrationMsgFallback")
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public boolean sendRegistrationMsg(StudentRegistrationProvinceBO studentRegistrationProvinceBO) {
        // 这里不需要 try-catch，让异常直接抛出给 Sentinel
        return sendRegistrationMQMsg(studentRegistrationProvinceBO);
    }

    /**
     * 发送报名MQ消息
     * <p>
     *
     * @return : boolean
     * @params : [studentRegistrationProvinceBO]
     **/
    private boolean sendRegistrationMQMsg(StudentRegistrationProvinceBO studentRegistrationProvinceBO) {
        // 这里的异常会向上抛出，触发外层的 Sentinel fallback
        boolean res = streamProducer.sendSyncSingleMsg(GsonUtils.object2Json(studentRegistrationProvinceBO));
        if (!res) {
            // 主动抛错，触发降级
            throw new RuntimeException("MQ发送失败");
        }
        return res;
    }

    /**
     * 发送报名MQ消息 fallback 方法
     * <p>
     * @params : [bo, t]
     * @return : boolean
     **/
    public boolean sendRegistrationMsgFallback(StudentRegistrationProvinceBO bo, Throwable t) {
        log.error("触发降级：MQ发送异常，转存Redis。异常信息：{}", t.getMessage());
        this.writeToRedis(bo);
        // 返回 true 表示降级处理成功（已留底），或者 false 看你业务定义
        return true;
    }

    /**
     * 写入 Redis 队列
     * @param studentRegistrationProvinceBO
     */
    private void writeToRedis(StudentRegistrationProvinceBO studentRegistrationProvinceBO) {
        try {
            Long l = redisUtil.lLeftPush("reg:downgrade:queue", GsonUtils.object2Json(studentRegistrationProvinceBO));
        } catch (Exception e) {
            log.error("严重事故：Redis 也挂了，写入本地日志", e);
            // 写本地文件 Log...
        }
    }


    /**
     * 注册学生
     * <p>
     *
     * @return : void
     * @params : [studentRegistrationProvinceBO]
     **/
    @Override
    public void registerStudent(StudentRegistrationProvinceBO studentRegistrationProvinceBO) {
        // 保存学生信息
        List<Student> res = studentService.list(new LambdaQueryWrapper<Student>()
                .eq(Student::getIdentityCode, studentRegistrationProvinceBO.getIdentityCode()));
        Student stu = Student.builder().build();
        if (res.isEmpty()) {
            String salt = BCrypt.gensalt();
            String hashPwd = BCrypt.hashpw("123456", salt);
            stu = Student.builder()
                    .identityCode(studentRegistrationProvinceBO.getIdentityCode())
                    .trueName(studentRegistrationProvinceBO.getTrueName())
                    .password(hashPwd)
                    .salt(salt)
                    .build();
            studentService.save(stu);
            // 保存默认角色
            studentRoleService.saveDefaultStudentRole(stu.getId());
            log.info(Constant.LOG_STYLE, "Saving Student And StudentRole to DB");
        } else {
            stu = res.get(0);
        }
        // 保存报名信息
        boolean save = this.save(StudentRegistration.builder()
                .studentId(stu.getId())
                .identityCode(studentRegistrationProvinceBO.getIdentityCode())
                .year(studentRegistrationProvinceBO.getYear())
                .registrationNum(studentRegistrationProvinceBO.getRegistrationNum())
                .used(YesOrNo.NO.type)
                .build());
        log.info(Constant.LOG_STYLE, "Saving StudentRegistration to DB");
        if (!save) {
            log.error(Constant.LOG_STYLE, "保存出错，插入log，人工补偿");
        }
    }

    /**
     * 清理mock数据
     * <p>
     *
     * @return : boolean
     * @params : [ids]
     **/
    @Override
    public boolean cleanMockData(List<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return false;
        }
        return studentRegistrationMapper.cleanMockData(ids);
    }


}
