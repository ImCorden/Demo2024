package com.bob.student.controller;

import com.bob.commontools.pojo.JsonResult;
import com.bob.student.bo.StudentRegistrationProvinceBO;
import com.bob.student.service.StudentRegistrationService;
import com.bob.student.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 学生报名资格 前端控制器
 * </p>
 *
 * @author Bob
 * @since 2024-11-04
 */
@Tag(name = "Registration Controller")
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/student/studentRegistration")
public class StudentRegistrationController {

    private final StudentRegistrationService studentRegistrationService;
    private final StudentService studentService;


    /**
     * 省厅报名接口
     * <p>
     *
     * @return : com.bob.commontools.pojo.JsonResult
     * @params : [studentRegistrationProvinceBO]
     **/
    @Operation(summary = "省厅报名接口")
    @PostMapping("province")
    public JsonResult registration(@RequestBody StudentRegistrationProvinceBO studentRegistrationProvinceBO) {
        if (studentRegistrationService.checkByIdentityCode(studentRegistrationProvinceBO)) {
            return JsonResult.ok("SUCCESS");
        }
        return JsonResult.errorMsg("ERROR");
    }


}
