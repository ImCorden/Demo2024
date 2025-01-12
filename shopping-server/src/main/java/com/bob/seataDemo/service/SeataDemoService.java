package com.bob.seataDemo.service;

import com.bob.commontools.exception.SeataDemoException;
import com.bob.seataDemo.domain.SeataDemo;
import com.baomidou.mybatisplus.extension.service.IService;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * <p>
 * Seata表用来实现分布式事务 服务类
 * </p>
 *
 * @author Bob
 * @since 2025-01-09
 */
@LocalTCC
public interface SeataDemoService extends IService<SeataDemo> {

    /**
     * TTC prepare 方法
     * <p>
     *
     * @return : java.lang.Boolean
     * @params : [seataDemo]
     **/
    @TwoPhaseBusinessAction(
            name = "TTCUpdate",
            commitMethod = "ttcUpdateCommit",
            rollbackMethod = "ttcUpdateRollback"
    )
    Boolean ttcUpdate(@BusinessActionContextParameter("newSeataDemo") SeataDemo seataDemo) throws SeataDemoException;

    /**
     * TTC commit 方法
     * <p>
     *
     * @return : java.lang.Boolean
     * @params : [businessActionContext]
     **/
    Boolean ttcUpdateCommit(BusinessActionContext businessActionContext) throws SeataDemoException;

    /**
     * TTC rollback 方法
     * <p>
     *
     * @return : java.lang.Boolean
     * @params : [businessActionContext]
     **/
    Boolean ttcUpdateRollback(BusinessActionContext businessActionContext) throws SeataDemoException;
}
