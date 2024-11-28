package com.bob.aspect;


/**
 * @ClassName : StudentUtils
 * @Description : TODO
 * @Author : Bob
 * @Date : 2024/11/18 21:20
 * @Version : 1.0
 **/
public class StudentHolder {

    private final static ThreadLocal<Long> requestHolder = new ThreadLocal<>();

    public static void add(Long id) {
        requestHolder.set(id);
    }

    public static Long getId() {
        return requestHolder.get();
    }

    public static void remove() {
        requestHolder.remove();
    }


}
