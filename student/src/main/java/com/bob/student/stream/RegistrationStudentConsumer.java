package com.bob.student.stream;


import com.bob.commontools.utils.ConvertUtil;
import com.bob.core.pojo.Constant;
import com.bob.student.bo.StudentRegistrationProvinceBO;
import com.bob.student.service.StudentRegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.impl.consumer.ConsumeMessageConcurrentlyService;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.ErrorMessage;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * @ClassName : RegistrationStream
 * @Description : 注册学生流程消费者
 *
 *
 * @Author : Bob
 * @Date : 2024/11/7 09:09
 * @Version : 1.0
 **/
@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class RegistrationStudentConsumer {

    private final StudentRegistrationService studentRegistrationService;

    /**
     * 消费者
     * <p>
     *
     * @return : java.util.function.Consumer<com.bob.student.bo.StudentRegistrationProvinceBO>
     * @params : []
     **/
    @Bean
    public Consumer<StudentRegistrationProvinceBO> regSave() {
        return message -> {
            log.info("-------------------------------------------------------------RegistrationConsumer接到消息：{}", message);
            // throw new RuntimeException(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>error");
            studentRegistrationService.registerStudent(message);
        };
    }

    /**
     * 错误消费处理
     * 如需验证，请启用 {@link RegistrationStudentConsumer#regSave} 中的异常
     * <p>
     * @params : []
     * @return : java.util.function.Consumer<org.springframework.messaging.support.ErrorMessage>
     **/
    @Bean
    public Consumer<ErrorMessage> myHandler() {
        return v -> {
            String msg = new String((byte[]) v.getOriginalMessage().getPayload());
            String localizedMessage = v.getPayload().getCause().getLocalizedMessage();
            String HeadersJson = ConvertUtil.beanToJson(v.getOriginalMessage().getHeaders());

            log.error(Constant.LOG_STYLE,"Registration Student Consumer 消费异常");
            log.error("原始消息：{}", msg);
            log.error("原始消息Headers：{}", HeadersJson);
            log.error("失败原因：{}", localizedMessage);
            log.error(Constant.LOG_STYLE,"END");
        };
    }

}
