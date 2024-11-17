package com.bob.role.service.impl;

import com.bob.role.domain.Role;
import com.bob.role.mapper.RoleMapper;
import com.bob.role.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author Bob
 * @since 2024-11-16
 */
@Service
public class RoleServiceImp extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
