package com.github.renny.loginsystem.interceptor;

import com.github.renny.loginsystem.security.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    private final JwtUtils jwtUtils;

    public JwtInterceptor(JwtUtils jwtUtils){
        this.jwtUtils = jwtUtils;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String authHeader = request.getHeader("Authorization");

        if(authHeader != null && authHeader.startsWith("Bearer ")){
            String token = authHeader.substring(7);
            try{
                String account = jwtUtils.getAccountFromToken(token);
                request.setAttribute("currentUserAccount",account);  //token解析出帳號
                request.setAttribute("userToken",token);
            }catch (Exception e){
                System.out.println("JWT解析失敗," + e.getMessage());
            }
        }

        return true;
    }
}
