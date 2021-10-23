package com.example.config;

import com.example.common.JacksonObjectMapper;
import com.example.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @ClassName WebConfig
 * @Description
 * @Author zhang
 * @Date 2021/10/13 16:43
 * @Version 1.0
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加登录拦截器
        registry.addInterceptor(new LoginCheckInterceptor())
                .addPathPatterns("/**")
                //对登录和静态资源进行放行
                .excludePathPatterns(
                        "/employee/login","/employee/logout",
                        "/backend/**","/front/**",
                        "/user/sendMsg","/user/login"
                );
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        //创建消息转换器对象
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        //设置对象转换器
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        // 添加到消息转换器集合，并设置index 0
        converters.add(0,messageConverter);
    }
}
