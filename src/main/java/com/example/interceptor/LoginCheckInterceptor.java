package com.example.interceptor;

import com.alibaba.fastjson.JSON;
import com.example.common.BaseContext;
import com.example.common.Contents;
import com.example.common.R;
import com.example.entity.Employee;
import com.example.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @ClassName LoginCheckInterceptor
 * @Description 用户登录验证拦截器
 * @Author zhang
 * @Date 2021/10/13 16:39
 * @Version 1.0
 **/
@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获得URI路径
        String requestURI = request.getRequestURI();
        //获得session对象
        HttpSession session = request.getSession();
        log.info("当前请求用户的JSESSIONID:{}",request.getRequestedSessionId());
        Employee employee = (Employee)session.getAttribute(Contents.EMPLOYEE_INFO);
        //TODO 同时登录前后台是 存在BUG
        if(employee!=null){
            //向当前线程中保存当前员工id
            BaseContext.setCurrentId(employee.getId());
            return true;
        }
        User user = (User)session.getAttribute(Contents.USER_INFO);
        if(user!=null){
            //向当前线程中保存当前用户id
            BaseContext.setCurrentId(user.getId());
            return true;
        }

        log.info("被拦截的路径为{}",requestURI);
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return false;
    }
}
