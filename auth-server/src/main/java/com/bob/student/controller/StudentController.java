package com.bob.student.controller;


import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bob.commontools.exception.BizException;
import com.bob.commontools.pojo.constants.RedisConstants;
import com.bob.commontools.pojo.JsonResult;
import com.bob.commontools.utils.GsonUtils;
import com.bob.commontools.utils.RedisUtil;
import com.bob.role.service.RolePermissionService;
import com.bob.student.bo.StudentLoginBo;
import com.bob.student.domain.Student;
import com.bob.student.service.StudentRoleService;
import com.bob.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
     *
     * @return : com.bob.commontools.pojo.JsonResult
     * @params : [identityCode, password]
     **/
    @PostMapping("login")
    public JsonResult Login(@RequestBody StudentLoginBo studentLoginBo) throws BizException {
        Student one = studentService.getOne(new LambdaQueryWrapper<Student>().eq(Student::getIdentityCode, studentLoginBo.getIdentityCode()));
        if (ObjectUtil.isNotNull(one)) {
            String pwd = one.getPassword();
            String salt = one.getSalt();
            // 加盐验证密码
            String hashPwd = BCrypt.hashpw(studentLoginBo.getPassword(), salt);
            if (hashPwd.equals(pwd)) {
                List<String> roleIds = studentRoleService.getRoleIdsByStudentId(one.getId())
                        .stream().map(String::valueOf).toList();
                String roleJsonStr = GsonUtils.object2Json(roleIds);
                // 向Redis缓存用户权限
                redisUtil.setEx(
                        RedisConstants.REDIS_USER_ROLES_LOGIN_KEY_PREFIX + String.valueOf(one.getId()),
                        roleJsonStr,
                        RedisConstants.REDIS_USER_ROLES_LOGIN_KEY_RENEW_TIME,
                        TimeUnit.SECONDS);
                StpUtil.login(one.getId(),
                        new SaLoginModel()
                                .setDevice(studentLoginBo.getDeviceType())
                );
                // 放入信息在Session中
                // StpUtil.getSession()
                //         .set("studentId", one.getId())
                //         .set("trueName", one.getTrueName())
                //         .set("identityCode", one.getIdentityCode())
                //         .set("roleIds", roleJsonStr);
                return JsonResult.ok("登录成功");
            }
            return JsonResult.errorMsg("---密码错误");
        }
        return JsonResult.errorMsg("----未注册");
    }

    /**
     * 退出接口
     * <p>
     *
     * @return : com.bob.commontools.pojo.JsonResult
     * @params : []
     **/
    @GetMapping("logout")
    public JsonResult logOut() {
        // 获取学生ID
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String token = requestAttributes.getRequest().getHeader("BobToken");
        if (ObjectUtil.isNotNull(token)) {
            Object loginIdByToken = StpUtil.getLoginIdByToken(token);
            StpUtil.logout(loginIdByToken);
            redisUtil.delete(RedisConstants.REDIS_USER_ROLES_LOGIN_KEY_PREFIX + loginIdByToken.toString());
            return JsonResult.ok();
        }
        return JsonResult.errorMsg("注销失败！");
    }
}
