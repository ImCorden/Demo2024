package com.bob.order.service.impl;


import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.bob.commontools.exception.BizException;
import com.bob.commontools.pojo.constants.BizConstants;
import com.bob.commontools.pojo.constants.RedisConstants;
import com.bob.commontools.pojo.vo.CourseInfoVO;
import com.bob.commontools.pojo.vo.StudyPlanCourseInfoVO;
import com.bob.commontools.pojo.vo.StudyPlanInfoVO;
import com.bob.commontools.utils.GsonUtils;
import com.bob.commontools.utils.RedisUtil;
import com.bob.core.SecKillRedisTools;
import com.bob.feignClients.studentServer.studyPlan.StudyPlanFeignClient;
import com.bob.feignClients.studentServer.studyPlanCourse.StudyPlanCourseFeignClient;
import com.bob.commontools.pojo.bo.SecKillItemAddBo;
import com.bob.commontools.pojo.bo.SecKillItemBo;
import com.bob.order.service.SecKillService;
import com.bob.stream.StreamProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName : SecKillServiceImp
 * @Description : TODO
 * @Author : Bob
 * @Date : 2024/12/2 20:01
 * @Version : 1.0
 **/
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecKillServiceImp implements SecKillService {

    private final RedisUtil redisUtil;
    private final SecKillRedisTools secKillRedisTools;
    // MQ消息
    private final StreamProducer streamProducer;
    // Feign
    private final StudyPlanFeignClient studyPlanFeignClient;
    private final StudyPlanCourseFeignClient studyPlanCourseFeignClient;

    /**
     * 添加秒杀商品
     * <p>
     *
     * @return : boolean
     * @params : [secKillCourseAddBo]
     **/
    @Override
    public boolean addSecKillItem(SecKillItemAddBo secKillItemAddBo) throws BizException {
        String secKillId = UUID.randomUUID().toString().replace("-", "");
        String stockKey = RedisConstants.REDIS_SEC_KILL_ITEM_STOCK_KEY_PREFIX + secKillId;
        String infoKey = null;
        Object item = switch (secKillItemAddBo.getItemType()) {
            case BizConstants.SEC_KILL_TYPE_COURSE -> {
                StudyPlanCourseInfoVO course = Optional.ofNullable(studyPlanCourseFeignClient.getStudyPlanCourseInfoById(secKillItemAddBo.getItemId()))
                        .orElseThrow(() -> new BizException("Feign调用错误，无法找到课程信息！"));
                infoKey = RedisConstants.REDIS_SEC_KILL_ITEM_COURSE_INFO_KEY_PREFIX + secKillId;
                yield course;
            }
            case BizConstants.SEC_KILL_TYPE_PLAN -> {
                StudyPlanInfoVO plan = Optional.ofNullable(studyPlanFeignClient.getStudyPlanInfoById(secKillItemAddBo.getItemId()))
                        .orElseThrow(() -> new BizException("Feign调用错误，无法找到计划信息！"));
                infoKey = RedisConstants.REDIS_SEC_KILL_ITEM_PLAN_INFO_KEY_PREFIX + secKillId;
                yield plan;
            }
            default -> null;
        };

        if (StrUtil.isNotBlank(infoKey)) {
            // 清空库存
            redisUtil.delete(stockKey);
            redisUtil.delete(infoKey);
            // 设置秒杀商品信息
            redisUtil.setEx(
                    infoKey,
                    GsonUtils.object2Json(item),
                    1, TimeUnit.HOURS
            );
            // 设置秒杀库存
            redisUtil.setEx(
                    stockKey,
                    secKillItemAddBo.getSecKillNum().toString(),
                    1, TimeUnit.HOURS);
            return true;
        }
        return false;
    }

    /**
     * 秒杀商品消息发送
     * <p>
     *
     * @return : boolean
     * @params : secKillCourseBo
     **/
    @Override
    public boolean sendSecKillMsg(SecKillItemBo secKillItemBo) {
        // 如果已经秒杀成功不发送消息
        // 如果使用先查询Redis，就违背了消息队列的使用意图：削峰，避免所有请求打进Redis中，所以关闭
        // boolean success = this.isSecKillSuccess(secKillCourseBo);
        // if (success) {
        //     return true;
        // }
        return streamProducer.sendSyncSingleMsg(GsonUtils.object2Json(secKillItemBo));
    }

    /**
     * 去秒杀库存扣减
     *
     * @param secKillItemBo
     * @return
     */
    @Override
    public boolean toSecKillItem(SecKillItemBo secKillItemBo) {
        String key = RedisConstants.REDIS_SEC_KILL_ITEM_STOCK_KEY_PREFIX + secKillItemBo.getSecKillId();
        boolean res = secKillRedisTools.callSecKillScript(key, "1");
        // log.info("秒杀结果：{}, 学生：{}", res, secKillCourseBo.getStudentId());
        return res;
    }

    /**
     * 创建OrderToken
     *
     * @param secKillItemBo
     */
    @Override
    public String createOrderToken(SecKillItemBo secKillItemBo) {
        String key = String.format(RedisConstants.REDIS_SEC_KILL_ITEM_SUCCESS_KEY,
                secKillItemBo.getSecKillId(),
                secKillItemBo.getStudentId());
        String token = UUID.fastUUID().toString().replaceAll("-", "");
        redisUtil.setEx(
                key,
                token,
                15,
                TimeUnit.MINUTES
        );
        return token;
    }

    /**
     * 查询是否秒杀成功过
     *
     * @param secKillItemBo
     * @return
     */
    @Override
    public boolean isSecKillSuccess(SecKillItemBo secKillItemBo) {
        String key = String.format(RedisConstants.REDIS_SEC_KILL_ITEM_SUCCESS_KEY,
                secKillItemBo.getSecKillId(),
                secKillItemBo.getStudentId());
        String token = redisUtil.get(key);
        return ObjectUtil.isNotNull(token);
    }

    @Override
    public List listSecKillItem(String secKillType) {
        return switch (secKillType) {
            case BizConstants.SEC_KILL_TYPE_COURSE -> {
                String key = RedisConstants.REDIS_SEC_KILL_ITEM_COURSE_INFO_KEY_PREFIX + "*";
                Set<String> keys = redisUtil.keys(key);
                yield redisUtil.multiGet(keys)
                        .stream()
                        .map(str -> GsonUtils.json2Object(str, StudyPlanCourseInfoVO.class))
                        .sorted(Comparator.comparingLong(StudyPlanCourseInfoVO::getId))
                        .toList();
            }
            case BizConstants.SEC_KILL_TYPE_PLAN -> {
                String key = RedisConstants.REDIS_SEC_KILL_ITEM_PLAN_INFO_KEY_PREFIX + "*";
                Set<String> keys = redisUtil.keys(key);
                yield redisUtil.multiGet(keys)
                        .stream()
                        .map(str -> GsonUtils.json2Object(str, StudyPlanInfoVO.class))
                        .sorted(Comparator.comparingLong(StudyPlanInfoVO::getId))
                        .toList();
            }
            default -> new ArrayList();
        };
    }
}
