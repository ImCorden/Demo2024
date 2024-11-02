package com.bob.student.controller;

import com.bob.commontools.pojo.JsonResult;
import com.bob.student.domain.Student;
import com.bob.student.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 学生表 前端控制器
 * </p>
 *
 * @author Bob
 * @since 2024-11-01
 */
@Tag(name = "StudentController")
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/student/student")
@Slf4j
public class StudentController {

    private final StudentService studentService;

    /**
     * @description : List学生
     * @params : []
     * @return : java.util.List<com.bob.student.domain.Student>
     * @date : 2024/11/1 17:17
     **/
    @Operation(summary = "list 学生")
    @GetMapping("list")
    public JsonResult list() {
        return JsonResult.ok(Optional.ofNullable(studentService.list()).orElse(new ArrayList<>()));
    }


}
