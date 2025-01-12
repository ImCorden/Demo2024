package com.bob.seataDemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.bob.commontools.exception.SeataDemoException;
import com.bob.commontools.utils.GsonUtils;
import com.bob.seataDemo.domain.SeataDemo;
import com.bob.seataDemo.mapper.SeataDemoMapper;
import com.bob.seataDemo.service.SeataDemoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * Seata表用来实现分布式事务 服务实现类
 * </p>
 *
 * @author Bob
 * @since 2025-01-09
 */
@Service
@Slf4j
public class SeataDemoServiceImp extends ServiceImpl<SeataDemoMapper, SeataDemo> implements SeataDemoService {


    /**
     * TTC prepare 方法
     * <p>
     *
     * @return : java.lang.Boolean
     * @params : [seataDemo]
     **/
    @Override
    public Boolean ttcUpdate(SeataDemo seataDemo) throws SeataDemoException {
        log.info("TTC prepare >>>>>>>>>>>>>>>>> xid:{}", RootContext.getXID());
        Optional.ofNullable(seataDemo.getId())
                .orElseThrow(() -> new SeataDemoException("id不能为空"));
        // 锁定
        boolean update = this.update(new LambdaUpdateWrapper<>(SeataDemo.class)
                .set(SeataDemo::getLockFlag, "1")
                .eq(SeataDemo::getId, seataDemo.getId())
                .eq(SeataDemo::getLockFlag, "0"));
        if (!update) {
            throw new SeataDemoException("锁定失败");
        }
        if (seataDemo.getAge() % 2 == 0){
            throw new SeataDemoException("模拟失败");
        }
        return true;
    }

    /**
     * TTC commit 方法
     * <p>
     *
     * @return : java.lang.Boolean
     * @params : [businessActionContext]
     **/
    @Override
    public Boolean ttcUpdateCommit(BusinessActionContext businessActionContext) throws SeataDemoException {
        log.info("TTC commit >>>>>>>>>>>>>>>>> xid:{}", businessActionContext.getXid());
        SeataDemo seataDemo = businessActionContext.getActionContext("newSeataDemo", SeataDemo.class);
        seataDemo.setLockFlag("0");
        boolean update = this.updateById(seataDemo);
        if (!update) {
            throw new SeataDemoException("更新失败");
        }
        return true;
    }

    /**
     * TTC rollback 方法
     * <p>
     *
     * @return : java.lang.Boolean
     * @params : [businessActionContext]
     **/
    @Override
    public Boolean ttcUpdateRollback(BusinessActionContext businessActionContext) throws SeataDemoException {
        log.info("TTC rollback >>>>>>>>>>>>>>>>> xid:{}", businessActionContext.getXid());
        log.info("TTC rollback >>>>>>>>>>>>>>>>> actionContext:{}", GsonUtils.toPrettyFormat(businessActionContext.getActionContext()));
        SeataDemo oldSeataDemo = businessActionContext.getActionContext("newSeataDemo", SeataDemo.class);
        // 解除锁定
        boolean rollBack = this.update(new LambdaUpdateWrapper<>(SeataDemo.class)
                .set(SeataDemo::getLockFlag, "0")
                .eq(SeataDemo::getId, oldSeataDemo.getId()));
        if (!rollBack) {
            throw new SeataDemoException("回滚失败");
        }
        return true;
    }
}
