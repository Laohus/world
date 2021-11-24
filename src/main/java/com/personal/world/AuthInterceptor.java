package com.personal.world;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession();
        if(session.getAttribute("source") == null){
            request.getRequestDispatcher("/login").forward(request, response);
            return false;
        };
        if(session.getAttribute("source").equals("system")){
            if(session.getAttribute("username") == null || session.getId()==null){
                request.getRequestDispatcher("/login").forward(request, response);
                return false;
            }
            return true;
        }else if (session.getAttribute("source").equals("qq")){
            if(session.getAttribute("openid") == null || session.getId()==null){
                request.getRequestDispatcher("/login").forward(request, response);
                return false;
            }
            return true;
        }else {
            request.getRequestDispatcher("/login").forward(request, response);
            return false;
        }


//        if (session.getAttribute("username") == null || session.getId()==null) {
//            request.getRequestDispatcher("/login").forward(request, response);
//            return false;
//        }
//        return true;
    }




}
