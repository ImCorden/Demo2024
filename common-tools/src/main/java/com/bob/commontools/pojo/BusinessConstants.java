package com.bob.commontools.pojo;


import lombok.Data;

/**
 * @ClassName : Constants
 * @Description : TODO
 *
 *
 * @Author : Bob
 * @Date : 2024/11/18 22:39
 * @Version : 1.0
 **/
@Data
public class BusinessConstants {


    //---------------- Redis ---------------------
    /**
     * 登录角色缓存Key前缀
     */
    public static final String REDIS_USER_ROLES_LOGIN_KEY_PREFIX = "userRoles:login:";
    /**
     * 登录角色ID缓存续签时间
     */
    public static final long REDIS_USER_ROLES_LOGIN_KEY_RENEW_TIME= 86400L;// 24h
    /**
     * SA TOKEN 登录缓存续签时间
     */
    public static final long REDIS_SA_TOKEN_LOGIN_RENEW_TIME= 86400L;// 24h





    //---------------- Http ---------------------
    /**
     * 透传StudentID的Header key名称
     */
    public static final String HEADER_STUDENT_ID_KEY = "X-Custom-Field-Student-Id";
}
