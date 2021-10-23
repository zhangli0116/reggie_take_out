package com.example.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.example.common.Contents;
import com.example.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @ClassName EmployeeMetaObjectHandler
 * @Description mybatis-plus 自动填充处理
 * @Author zhang
 * @Date 2021/10/14 10:10
 * @Version 1.0
 **/
/*
自定义元数据处理器
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

    //注入springmvc容器中的 HttpSession对象
    /*@Autowired
    private HttpSession httpSession;*/
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill..." + this);

        //添加创建时间
        metaObject.setValue("createTime",LocalDateTime.now());
        //添加修改时间
        metaObject.setValue("updateTime",LocalDateTime.now());
        /*Employee userInfo = (Employee)httpSession.getAttribute(Contents.EMPLOYEE_INFO);
        Long userId2 = userInfo.getId();*/
        //获得当前线程id
        Long userId = BaseContext.getCurrentId();
        //添加创建者id
        metaObject.setValue("createUser",userId);
        //添加修改者id
        metaObject.setValue("updateUser",userId);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill...." + this);

       /* Employee userInfo = (Employee)httpSession.getAttribute(Contents.EMPLOYEE_INFO);
        Long userId2 = userInfo.getId();*/
        //获得当前线程id
        Long userId = BaseContext.getCurrentId();
        //添加修改时间
        metaObject.setValue("updateTime",LocalDateTime.now());
        //添加修改者id
        metaObject.setValue("updateUser",userId);
    }
}
