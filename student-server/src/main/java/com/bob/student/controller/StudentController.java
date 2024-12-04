package com.bob.student.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bob.commontools.pojo.JsonResult;
import com.bob.aspect.StudentHolder;
import com.bob.student.domain.Student;
import com.bob.student.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 学生表 前端控制器
 * </p>
 *
 * @author Bob
 * @since 2024-11-04
 */
@Slf4j
@Tag(name = "Student Controller")
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/student/student")
public class StudentController {

    private final StudentService studentService;

    @Operation(summary = "按学生Id查找学生详细信息")
    @GetMapping("findById/{studentId}")
    public JsonResult findByStudentId(@PathVariable Long studentId){
        Long id = StudentHolder.getId();
        log.info("----------------------------hoder:{}",id);
        Student res = studentService.getOne(new LambdaQueryWrapper<Student>().eq(Student::getId, studentId));
        return JsonResult.ok(res);
    }


    @GetMapping("testJmx")
    public JsonResult testJmx(){
        return JsonResult.ok();
    }



}
