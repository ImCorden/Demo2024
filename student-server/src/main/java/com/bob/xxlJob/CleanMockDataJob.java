package com.bob.xxlJob;


import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bob.student.domain.Student;
import com.bob.student.domain.StudentRegistration;
import com.bob.student.domain.StudentRole;
import com.bob.student.service.StudentRegistrationService;
import com.bob.student.service.StudentRoleService;
import com.bob.student.service.StudentService;
import com.xxl.job.core.context.XxlJobContext;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.*;

/**
 * @ClassName : CleanMockDataJob
 * @Description : student-server 清理恢复初始数据的定时任务，配置文件在common-tools
 * @Author : Bob
 * @Date : 2024/12/5 10:34
 * @Version : 1.0
 **/
@Slf4j
@Data
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CleanMockDataJob {

    private final StudentService studentService;
    private final StudentRegistrationService studentRegistrationService;
    private final StudentRoleService studentRoleService;
    private Executor executor = Executors.newFixedThreadPool(3);

    @XxlJob(value = "stuMockDataCleanHandler")
    public void execute() {
        long start = System.currentTimeMillis();
        int index = XxlJobHelper.getShardIndex();
        int total = XxlJobHelper.getShardTotal();
        String jobParam = XxlJobHelper.getJobParam();
        String jobLogFileName = XxlJobHelper.getJobLogFileName();
        XxlJobHelper.log("jobLogFileName:{},index = {},total={},param={}", jobLogFileName, index, total, jobParam);

        XxlJobContext xxlJobContext = XxlJobContext.getXxlJobContext();
        // 异步开始删除
        CompletableFuture<Boolean> stuFuture = CompletableFuture.supplyAsync(() -> {
            return cleanStudent(index, total, xxlJobContext);
        }, executor).exceptionally(e -> {
            XxlJobHelper.log("clean student error:{}", e.getMessage());
            return false;
        });

        CompletableFuture<Boolean> stuRoleFuture = CompletableFuture.supplyAsync(() -> {
            return cleanStudentRole(index, total, xxlJobContext);
        }, executor).exceptionally(e -> {
            XxlJobHelper.log("clean student role error:{}", e.getMessage());
            return false;
        });

        CompletableFuture<Boolean> stuRegFuture = CompletableFuture.supplyAsync(() -> {
            return cleanStudentReg(index, total, xxlJobContext);
        }, executor).exceptionally(e -> {
            XxlJobHelper.log("clean student reg error:{}", e.getMessage());
            return false;
        });

        CompletableFuture<Void> allOf = CompletableFuture.allOf(stuFuture, stuRoleFuture, stuRegFuture);
        try {
            allOf.get(5, TimeUnit.MINUTES);
            if (!(stuFuture.get() & stuRoleFuture.get() & stuRegFuture.get())) {
                // 返回执行失败
                XxlJobHelper.handleFail("表清理存在失败，请检查log！");
            }
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            // e.printStackTrace();
            // 返回执行失败
            XxlJobHelper.handleFail("执行出异常：" + e.getMessage());
        }
        long end = System.currentTimeMillis();
        XxlJobHelper.log("stuMockDataCleanHandler执行完成，耗时：{}ms", end - start);
    }

    /**
     * 清理学生表
     *
     * @param index
     * @param total
     * @param xxlJobContext
     * @return
     */
    private boolean cleanStudent(int index, int total, XxlJobContext xxlJobContext) {
        XxlJobContext.setXxlJobContext(xxlJobContext);
        long stuMockNum = studentService.count(new LambdaQueryWrapper<Student>().gt(Student::getId, 1000));
        if (stuMockNum == 0) {
            XxlJobHelper.log("当前【数据库】没有需要处理的Student Mock数据！");
            return true;
        }
        String limit = getLimit(stuMockNum, index, total);
        List<Long> ids = studentService.listObjs(new LambdaQueryWrapper<Student>()
                .select(Student::getId)
                .gt(Student::getId, 1000)
                .last("limit " + limit)
        );
        if (ids.isEmpty()) {
            XxlJobHelper.log("当前【分片任务】没有需要处理的Student Mock数据！");
            return true;
        }
        // boolean res = studentService.removeByIds(ids);
        boolean res = false;
        if (ids.size() < 1000) {
            res = studentService.cleanMockData(ids);
        } else {
            List<List<Long>> partition = ListUtil.partition(ids, 1000);
            partition.forEach(list -> {
                studentService.cleanMockData(ids);
            });
            res = true;
        }
        XxlJobHelper.log("清理完成：Student表，本分片的limit参数:{}, 清理数量:{}, 清理结果:{}", limit, ids.size(), res);
        return res;
    }

    /**
     * 清理StudentRegistration表
     * <p>
     *
     * @return : boolean
     * @params : [index, total, xxlJobContext]
     **/
    private boolean cleanStudentReg(int index, int total, XxlJobContext xxlJobContext) {
        // 子线程重新设置xxl上下文
        XxlJobContext.setXxlJobContext(xxlJobContext);

        long stuRegMockNum = studentRegistrationService.count(new LambdaQueryWrapper<StudentRegistration>().gt(StudentRegistration::getId, 30));
        if (stuRegMockNum == 0) {
            XxlJobHelper.log("当前【数据库】没有需要处理的StudentRegistration Mock数据！");
            return true;
        }
        String limit = getLimit(stuRegMockNum, index, total);
        List<Long> ids = studentRegistrationService.listObjs(new LambdaQueryWrapper<StudentRegistration>()
                .select(StudentRegistration::getId)
                .gt(StudentRegistration::getId, 30)
                .last("limit " + limit)
        );
        if (ids.isEmpty()) {
            XxlJobHelper.log("当前【分片任务】没有需要处理的StudentRegistration Mock数据！");
            return true;
        }
        // boolean res = studentRegistrationService.removeByIds(ids);
        boolean res = false;
        if (ids.size() < 1000) {
            res = studentRegistrationService.cleanMockData(ids);
        } else {
            List<List<Long>> partition = ListUtil.partition(ids, 1000);
            partition.forEach(list -> {
                studentRegistrationService.cleanMockData(ids);
            });
            res = true;
        }
        XxlJobHelper.log("清理完成：StudentRegistration表，本分片的limit参数:{}, 清理数量:{}, 清理结果:{}", limit, ids.size(), res);
        return res;
    }

    /**
     * 清理StudentRole表
     * <p>
     *
     * @return : boolean
     * @params : [index, total, xxlJobContext]
     **/
    private boolean cleanStudentRole(int index, int total, XxlJobContext xxlJobContext) {
        XxlJobContext.setXxlJobContext(xxlJobContext);
        long stuRoleMockNum = studentRoleService.count(new LambdaQueryWrapper<StudentRole>().gt(StudentRole::getId, 20));
        if (stuRoleMockNum == 0) {
            XxlJobHelper.log("当前【数据库】没有需要处理的StudentRole Mock数据！");
            return true;
        }
        String limit = getLimit(stuRoleMockNum, index, total);
        List<Long> ids = studentRoleService.listObjs(new LambdaQueryWrapper<StudentRole>()
                .select(StudentRole::getId)
                .gt(StudentRole::getId, 20)
                .last("limit " + limit)
        );
        if (ids.isEmpty()) {
            XxlJobHelper.log("当前【分片任务】没有需要处理的StudentRole Mock数据！");
            return true;
        }
        // boolean res = studentRoleService.removeByIds(ids);
        // 默认是逻辑删除，但是清空mock数据需要物理删除，所以改为自定义方法
        boolean res = false;
        if (ids.size() < 1000) {
            res = studentRoleService.cleanMockData(ids);
        } else {
            List<List<Long>> partition = ListUtil.partition(ids, 1000);
            partition.forEach(list -> {
                studentRoleService.cleanMockData(ids);
            });
            res = true;
        }
        XxlJobHelper.log("清理完成：StudentRole表，本分片的limit参数:{}, 清理数量:{}, 清理结果:{}", limit, ids.size(), res);
        return res;
    }


    /**
     * 获取limit参数
     *
     * @param dataNum
     * @param index
     * @param total
     * @return
     */
    private String getLimit(long dataNum, int index, int total) {
        // 向上取整步数
        int step = Double.valueOf(Math.ceil(NumberUtil.div(dataNum, total))).intValue();
        int start = NumberUtil.round(NumberUtil.mul(step, index), 0).intValue();
        return start + "," + step;
    }

}
