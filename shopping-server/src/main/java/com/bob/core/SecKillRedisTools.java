package com.bob.core;


import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName : RedisTools
 * @Description : TODO
 * @Author : Bob
 * @Date : 2024/12/2 11:02
 * @Version : 1.0
 **/
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Data
public class SecKillRedisTools {

    private final RedissonClient redissonClient;

    /**
     * 在系统启动时，将脚本预加载到Redis中，并返回一个加密的字符串，下次只要传该加密窜，即可执行对应脚本，减少了Redis的预编译
     */
    private String STORE_DEDUCTION_SCRIPT_SHA1 = "";

    /**
     * lua逻辑：首先判断活动库存是否存在，以及库存余量是否够本次购买数量，如果不够，则返回0，如果够则完成扣减并返回1
     * 两个入参，KEYS[1] : 活动库存的key
     * KEYS[2] : 活动库存的扣减数量
     */
    // private String STORE_DEDUCTION_SCRIPT_LUA =
    //         "local c_s = redis.call('get', KEYS[1])\n" +
    //                 "if not c_s or tonumber(c_s) < tonumber(KEYS[2]) then\n" +
    //                 "return 0\n" +
    //                 "end\n" +
    //                 "redis.call('decrby',KEYS[1], KEYS[2])\n" +
    //                 "return 1";
    private String STORE_DEDUCTION_SCRIPT_LUA =
            """
                    -- 调用Redis的get指令，查询活动库存，其中KEYS[1]为传入的参数1，即库存key
                    local c_s = redis.call('get', KEYS[1])
                    -- 判断活动库存是否充足，其中KEYS[2]为传入的参数2，即当前抢购数量
                    if not c_s or tonumber(c_s) < tonumber(KEYS[2]) then
                       return 0
                    end
                    -- 如果活动库存充足，则进行扣减操作。其中KEYS[2]为传入的参数2，即当前抢购数量
                    redis.call('decrby',KEYS[1], KEYS[2])
                    """;


    @PostConstruct
    public void init() {
        // 获取 RScript 对象
        RScript rScript = redissonClient.getScript(StringCodec.INSTANCE);
        // 使用 SCRIPT LOAD 命令将脚本加载到 Redis
        STORE_DEDUCTION_SCRIPT_SHA1 = rScript.scriptLoad(STORE_DEDUCTION_SCRIPT_LUA);
        log.info("生成的sha1：{}", STORE_DEDUCTION_SCRIPT_SHA1);
    }

    /**
     * 执行秒杀脚本
     * <p>
     * @params : [itemId]
     * @return : boolean
     **/
    public boolean callSecKillScript(String key,String num) {
        RScript script = redissonClient.getScript(StringCodec.INSTANCE);
        Object result = script.evalSha(
                RScript.Mode.READ_WRITE,
                this.STORE_DEDUCTION_SCRIPT_SHA1,
                RScript.ReturnType.VALUE,
                List.of(key, num)
        );
        return ObjectUtil.isNull(result);
    }
}