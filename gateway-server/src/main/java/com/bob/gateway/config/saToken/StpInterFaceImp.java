package com.bob.gateway.config.saToken;


import cn.dev33.satoken.stp.StpInterface;
import com.bob.commontools.pojo.BusinessConstants;
import com.bob.commontools.utils.GsonHelper;
import com.bob.commontools.utils.RedisOperator;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName : SaToken
 * @Description : TODO
 * @Author : Bob
 * @Date : 2024/11/17 22:42
 * @Version : 1.0
 **/
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StpInterFaceImp implements StpInterface {

    private final RedisOperator redisOperator;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {

        List<String> res = new ArrayList<>();

        // 获取Sa-Token Session中用户登录时候的RoleIds
        List<String> userRoleIds = this.getRoleList(loginId, loginType);
        log.info("----------当前用户RoleIds为：{}", GsonHelper.object2Json(userRoleIds));

        // 获取缓存在Redis中的所有Permission
        Map<Object, Object> allPermissions = redisOperator.hgetall("permissions");

        // 取出用户Permission
        allPermissions.forEach((k, v) -> {
            if (userRoleIds.contains(k.toString())) {
                res.addAll(GsonHelper.json2Object(v.toString(), new TypeToken<List<String>>() {
                }.getType()));
            }
        });
        log.info("----------当前用户权限为：{}", GsonHelper.object2Json(res));
        return res;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        // 从Sa Token Session中获取用户所有RoleIds
        // return StpUtil.getSessionByLoginId(loginId)
        //         .get("roleIds", new ArrayList<Long>())
        //         .stream()
        //         .map(String::valueOf)
        //         .toList();

        // 改为从Redis中取权限，防止不重新登录无法刷新用户权限变更
        String userRoles = redisOperator.get(BusinessConstants.REDIS_USER_ROLES_LOGIN_KEY_PREFIX + loginId);
        List<Long> roles = GsonHelper.json2Object(userRoles, new TypeToken<List<Long>>() {
        }.getType());
        return roles.stream().map(String::valueOf).toList();
    }
}
