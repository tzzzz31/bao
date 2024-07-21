package com.bao.bookforum.intercepter;

import com.bao.bookforum.entity.Admin;
import com.bao.bookforum.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Slf4j
public class UserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User user =(User) session.getAttribute("user");
        if(Objects.isNull(session.getAttribute("user"))){
            //未登录或登录过期
//            response.sendError(HttpStatus.HTTP_NOT_FOUND);
            //未登录，重定向到登录页面
            response.setContentType("text/html"); response.setCharacterEncoding("gb2312");
            response.getWriter().println("<script language='javascript' charset=utf-8>" + "alert('想要使用此功能请先登录！');"+"window.location.href='/login';</script>')");
//            response.sendRedirect("/login");
            return false;
        }
        if (user.getUserFrozen()==1){
            //被封禁了
            System.out.println("被ban了");
            session.removeAttribute("user");
            response.sendRedirect("/ban");
            return false;
        }

        return true;
    }
}
