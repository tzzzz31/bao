package com.bao.bookforum.common;

import com.bao.bookforum.intercepter.AdminIntercepter;
import com.bao.bookforum.intercepter.UserInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GlobalConfig implements WebMvcConfigurer {

    @Bean
    public HandlerInterceptor getAdminIntercepter(){
        return new AdminIntercepter();
    }
    @Bean
    public HandlerInterceptor getUserInterceptor(){
        return new UserInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        //管理员拦截器
        registry.addInterceptor(getAdminIntercepter()).addPathPatterns("/bao123/**").excludePathPatterns("/bao123/login","/bao123/logout","/bao123/adminLogin");
       //用户拦截器
        registry.addInterceptor(getUserInterceptor()).addPathPatterns("/user/**");
//        registry.addInterceptor(getAdminIntercepter()).addPathPatterns("/user");
    }
}
