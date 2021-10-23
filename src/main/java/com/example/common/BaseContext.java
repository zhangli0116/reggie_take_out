package com.example.common;

/**
 * @ClassName BaseContext
 * @Description 基于ThreadLocal封装的工具类,用于保持和获取当前用户的id
 * @Author zhang
 * @Date 2021/10/16 10:24
 * @Version 1.0
 **/
public class BaseContext {

    //每一个线程单独保持自己的id
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
