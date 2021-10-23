package com.example.common;

import com.example.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @ClassName GlobalExceptionHandler
 * @Description
 * @Author zhang
 * @Date 2021/10/14 11:43
 * @Version 1.0
 **/
//指定给特定的注解类进行增强
@RestControllerAdvice(annotations = RestController.class)
@Slf4j
public class GlobalExceptionHandler {

    //处理异常
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> handleException(Exception e){
        //Duplicate entry 'zhangsan' for key 'employee.idx_username
        //log.error(e.getMessage());
        e.printStackTrace();
        return R.error("出现异常错误");
    }

    //处理自定义异常
    @ExceptionHandler(CustomException.class)
    public R<String> handleCustomException(CustomException e){
        e.printStackTrace();
        return R.error(e.getMessage());
    }
}
