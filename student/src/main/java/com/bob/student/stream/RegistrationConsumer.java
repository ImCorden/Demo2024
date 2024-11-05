package com.bob.student.stream;


import com.bob.student.bo.StudentRegistrationProvinceBO;
import com.bob.student.service.StudentRegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

/**
 * @ClassName : RegistrationConsumer
 * @Description : TODO
 * @Author : Bob
 * @Date : 2024/11/5 12:02
 * @Version : 1.0
 **/
@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class RegistrationConsumer {

    private final StudentRegistrationService studentRegistrationService;

    /**
     * 消费者
     * <p>
     *
     * @return : java.util.function.Consumer<com.bob.student.bo.StudentRegistrationProvinceBO>
     * @params : []
     **/
    @Bean
    public Consumer<StudentRegistrationProvinceBO> registration() {
        return message -> {
            log.info("-------------------------------------------------------------RegistrationConsumer接到消息：{}", message);
            studentRegistrationService.registerStudent(message);
        };
    }


}
