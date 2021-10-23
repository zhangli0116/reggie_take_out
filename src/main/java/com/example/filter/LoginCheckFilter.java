package com.example.filter;

import com.alibaba.fastjson.JSON;
import com.example.common.BaseContext;
import com.example.common.Contents;
import com.example.common.R;
import com.example.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @ClassName LoginCheckFilter
 * @Description
 * @Author zhang
 * @Date 2021/10/13 16:10
 * @Version 1.0
 **/
@Slf4j
//@WebFilter(urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    //路径匹配，支持通配符

    public static final AntPathMatcher PATH_MATHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        String requestURI = request.getRequestURI();
        String[] patterns = new String[]{
                "/employee/login","/employee/logout",
                "/backend/**","/front/**"
        };

        boolean match = match(patterns, requestURI);
        if(match){
            //如果路径匹配则放行
            filterChain.doFilter(request,response);
            return;
        }
        HttpSession session = request.getSession();
        Employee userInfo = (Employee)session.getAttribute(Contents.EMPLOYEE_INFO);
        if(userInfo!=null){
            //向当前线程中保持当前用户id
            BaseContext.setCurrentId(userInfo.getId());
            //如果session中存在用户则放行
            filterChain.doFilter(request,response);
            return;
        }
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        //response.sendRedirect("/backend/page/login/login.html");
        return;
    }

    public boolean match(String[] patterns,String requestURI){
        for (String pattern : patterns) {
            boolean match = PATH_MATHER.match(pattern, requestURI);
            if(match){
                return true;
            }
        }
        return false;
    }
}
