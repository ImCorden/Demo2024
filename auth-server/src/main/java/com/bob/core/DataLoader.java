package com.bob.core;


 
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bob.commontools.utils.GsonHelper;
import com.bob.commontools.utils.RedisOperator;
import com.bob.commontools.utils.RedisUtil;
import com.bob.role.domain.RolePermission;
import com.bob.role.service.RolePermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName : DataLoader
 * @Description : 项目启动时自动加载权限到Redis
 * @Author : Bob
 * @Date : 2024/11/16 18:53
 * @Version : 1.0
 **/
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DataLoader implements ApplicationRunner {

    private final RedisUtil redisUtil;

    private final RolePermissionService rolePermissionService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 删除之前缓存
        redisUtil.delete("permissions");
        // 获取所有Role 对应的Permission
        Map<Long, List<String>> roleIds = rolePermissionService.list(
                        new LambdaQueryWrapper<RolePermission>()
                                .select(RolePermission::getRoleId, RolePermission::getPermission)
                )
                .stream()
                .collect(Collectors.groupingBy(
                        RolePermission::getRoleId,
                        Collectors.mapping(RolePermission::getPermission, Collectors.toList())));
        // 加载到Redis中
        roleIds.forEach((roleId, permissionIdList) -> {
            String list = GsonHelper.object2Json(permissionIdList);
            redisUtil.hPut("permissions", roleId.toString(), list);
            log.info("---加载Role到Redis---->  {}:{}", roleId.toString(), list);
        });


    }
}
