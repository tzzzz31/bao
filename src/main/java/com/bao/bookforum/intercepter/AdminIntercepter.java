package com.bao.bookforum.intercepter;

import cn.hutool.http.HttpStatus;
import com.bao.bookforum.entity.Admin;
import com.bao.bookforum.exception.GlobalExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Slf4j
public class AdminIntercepter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Admin admin =(Admin) session.getAttribute("admin");
        if(Objects.isNull(session.getAttribute("admin"))){
            //未登录或登录过期
//            response.sendError(HttpStatus.HTTP_NOT_FOUND);
            //未登录，重定向到登录页面
            response.sendRedirect("/bao123/login");
            return false;
        }


        return true;
    }
}
