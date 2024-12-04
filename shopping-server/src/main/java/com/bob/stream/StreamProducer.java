package com.bob.stream;


import com.bob.commontools.pojo.constants.StreamConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @ClassName : StreamProducer
 * @Description : TODO
 *
 *
 * @Author : Bob
 * @Date : 2024/12/3 10:54
 * @Version : 1.0
 **/
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class StreamProducer {

    private final StreamBridge streamBridge;

      /**
     * 发送普通同步消息（生产者）
     * <p>
     *
     * @return : boolean
     * @params : [message]
     **/
    public boolean sendSyncSingleMsg(String message) {
        // log.info("-------------------------------------------------------------SecKillProcessor准备开始发送消息：{}", message);
        return streamBridge.send(
                StreamConstants.SEC_KILL_PROCESSOR,
                MessageBuilder.withPayload(message).build()
        );
    }
}
