package com.bob.student.controller;


import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bob.commontools.exception.BusinessException;
import com.bob.commontools.pojo.BusinessConstants;
import com.bob.commontools.pojo.JsonResult;
import com.bob.commontools.utils.GsonHelper;
import com.bob.commontools.utils.RedisUtil;
import com.bob.core.aop.NeedStudentInHeader;
import com.bob.core.aop.StudentHolder;
import com.bob.role.service.RolePermissionService;
import com.bob.student.domain.Student;
import com.bob.student.service.StudentRoleService;
import com.bob.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
    private final RolePermissionService rolePermissionService;
    private final RedisUtil redisUtil;

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
                List<String> roleIds = studentRoleService.getRoleIdsByStudentId(one.getId())
                        .stream().map(String::valueOf).toList();
                // 向Redis缓存用户权限
                redisUtil.setEx(
                        BusinessConstants.REDIS_USER_ROLES_LOGIN_KEY_PREFIX + String.valueOf(one.getId()),
                        GsonHelper.object2Json(roleIds),
                        BusinessConstants.REDIS_USER_ROLES_LOGIN_KEY_RENEW_TIME,
                        TimeUnit.MILLISECONDS);
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

    /**
     * 退出接口
     * <p>
     * @params : []
     * @return : com.bob.commontools.pojo.JsonResult
     **/
    @GetMapping("logout")
    @NeedStudentInHeader
    public JsonResult logOut(){
        // 获取学生ID
        Long studentId = StudentHolder.getId();
        if (StpUtil.isLogin(studentId)){
            StpUtil.logout(studentId);
        }
        redisUtil.delete(BusinessConstants.REDIS_USER_ROLES_LOGIN_KEY_PREFIX + studentId );
        return JsonResult.ok();
    }

}
