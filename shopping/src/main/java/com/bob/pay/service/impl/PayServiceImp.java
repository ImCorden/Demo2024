package com.bob.pay.service.impl;

import com.bob.pay.domain.Pay;
import com.bob.pay.mapper.PayMapper;
import com.bob.pay.service.PayService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付 服务实现类
 * </p>
 *
 * @author Bob
 * @since 2024-11-04
 */
@Service
public class PayServiceImp extends ServiceImpl<PayMapper, Pay> implements PayService {

}
