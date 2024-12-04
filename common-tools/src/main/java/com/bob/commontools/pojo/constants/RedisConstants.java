package com.bob.commontools.pojo.constants;


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
public class RedisConstants {


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


     /**
     * Redis秒杀库存商品秒杀库存缓存Key前缀
     */
    public static final String REDIS_SEC_KILL_ITEM_STOCK_KEY_PREFIX = "seckill:stock:";
    /**
     * Redis秒杀库存商品信息缓存Key前缀(课程)
     */
    public static final String REDIS_SEC_KILL_ITEM_COURSE_INFO_KEY_PREFIX = "seckill:info:courses:";
    /**
     * Redis秒杀库存商品信息缓存Key前缀(计划)
     */
    public static final String REDIS_SEC_KILL_ITEM_PLAN_INFO_KEY_PREFIX = "seckill:info:plans:";
    /**
     * Redis秒杀成功Token
     */
    public static final String REDIS_SEC_KILL_ITEM_SUCCESS_KEY = "seckill:success:%s:%s";



}
