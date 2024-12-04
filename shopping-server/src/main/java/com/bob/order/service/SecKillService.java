package com.bob.order.service;

import com.bob.commontools.exception.BizException;
import com.bob.commontools.pojo.bo.SecKillItemAddBo;
import com.bob.commontools.pojo.bo.SecKillItemBo;

import java.util.List;

/**
 * 秒杀服务类
 * <p>
 * @params :
 * @return :
 **/
public interface SecKillService {

    /**
     * 添加秒杀商品
     * <p>
     * @params : [secKillCourseAddBo]
     * @return : boolean
     **/
    boolean addSecKillItem(SecKillItemAddBo secKillItemAddBo) throws BizException;

    /**
     * 秒杀商品消息发送
     * @param secKillItemBo
     * @return
     */
    boolean sendSecKillMsg(SecKillItemBo secKillItemBo);

    /**
     * 去秒杀库存扣减
     * @param secKillItemBo
     * @return
     */
    boolean toSecKillItem(SecKillItemBo secKillItemBo);

    /**
     * 创建OrderToken
     * @param secKillItemBo
     */
    String createOrderToken(SecKillItemBo secKillItemBo);

    /**
     * 查询是否秒杀成功过
     * @param secKillItemBo
     * @return
     */
    boolean isSecKillSuccess(SecKillItemBo secKillItemBo);

    /**
     * 按照类型获取所有秒杀商品信息
     * @param secKillType
     * @return
     */
    List listSecKillItem(String secKillType);
}
