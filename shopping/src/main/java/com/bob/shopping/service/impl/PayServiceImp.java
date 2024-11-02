package com.bob.shopping.service.impl;

import com.bob.shopping.domain.Pay;
import com.bob.shopping.mapper.PayMapper;
import com.bob.shopping.service.PayService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付 服务实现类
 * </p>
 *
 * @author Bob
 * @since 2024-11-01
 */
@Service
public class PayServiceImp extends ServiceImpl<PayMapper, Pay> implements PayService {

}
