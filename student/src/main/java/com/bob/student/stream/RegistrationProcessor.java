package com.bob.student.stream;


import com.bob.core.pojo.Constant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @ClassName : RegistrationProcrssor
 * @Description : 省厅学生注册后续流程MQ生产者
 * @Author : Bob
 * @Date : 2024/11/5 14:13
 * @Version : 1.0
 **/

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class RegistrationProcessor {

    private final StreamBridge streamBridge;

    /**
     * 发送消息
     * <p>
     *
     * @return : boolean
     * @params : [message]
     **/
    public boolean sendMessage(String message) {
        log.info("-------------------------------------------------------------RegistrationProcessor准备开始发送消息：{}", message);
        return streamBridge.send(
                Constant.REGISTERATION_PROCRESSOR,
                MessageBuilder.withPayload(message).build()
        );
    }
}
