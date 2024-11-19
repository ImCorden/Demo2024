package com.bob.student.controller;

import cn.hutool.core.util.ObjectUtil;
import com.bob.commontools.pojo.JsonResult;
import com.bob.student.domain.StudentRole;
import com.bob.student.service.StudentRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 学生角色表 前端控制器
 * </p>
 *
 * @author Bob
 * @since 2024-11-16
 */
@RestController
@RequestMapping("/student/studentRole")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class StudentRoleController {

    private final StudentRoleService studentRoleService;

}
