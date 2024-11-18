package com.bob.student.controller;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bob.commontools.exception.BusinessException;
import com.bob.commontools.pojo.JsonResult;
import com.bob.commontools.utils.GsonHelper;
import com.bob.commontools.utils.RedisOperator;
import com.bob.student.domain.Student;
import com.bob.student.mapper.StudentMapper;
import com.bob.student.service.StudentRoleService;
import com.bob.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 学生表 前端控制器
 * </p>
 *
 * @author Bob
 * @since 2024-11-14
 */
@Slf4j
@RestController
@RequestMapping("/student/student")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StudentController {

    private final StudentService studentService;
    private final StudentRoleService studentRoleService;
    private final RedisOperator redisOperator;

    /**
     * 登录接口
     * <p>
     * @params : [identityCode, password]
     * @return : com.bob.commontools.pojo.JsonResult
     **/
    @PostMapping("login")
    public JsonResult Login(@RequestParam String identityCode, @RequestParam String password) throws BusinessException {
        Student one = studentService.getOne(new LambdaQueryWrapper<Student>().eq(Student::getIdentityCode, identityCode));
        if (ObjectUtil.isNotNull(one)) {
            String pwd = one.getPassword();
            String salt = one.getSalt();
            // 加盐验证密码
            String hashPwd = BCrypt.hashpw(password, salt);
            if (hashPwd.equals(pwd)) {
                List<Long> roleIds = studentRoleService.getRoleIdsByStudentId(one.getId());
                // 向Redis缓存用户权限
                redisOperator.hset("UserRoles",String.valueOf(one.getId()), GsonHelper.object2Json(roleIds));
                StpUtil.login(one.getId());
                // 放入信息在Session中
                StpUtil.getSession()
                        .set("studentId", one.getId())
                        .set("trueName", one.getTrueName())
                        .set("identityCode", one.getIdentityCode())
                        .set("roleIds", roleIds);
                return JsonResult.ok("登录成功");
            }
            return JsonResult.errorMsg("---密码错误");
        }
        return JsonResult.errorMsg("----未注册");
    }

}
