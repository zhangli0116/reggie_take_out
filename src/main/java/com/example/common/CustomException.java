package com.example.common;


/**
 * @ClassName CustomException
 * @Description 自定义异常类
 * @Author zhang
 * @Date 2021/10/16 16:27
 * @Version 1.0
 **/
public class CustomException extends RuntimeException{
    public CustomException(String msg){
        super(msg);
    }
}
