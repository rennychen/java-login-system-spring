package com.github.renny.loginsystem.interceptor;

import com.github.renny.loginsystem.security.JwtUtils;
import com.github.renny.loginsystem.service.TokenBlacklistService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    private final JwtUtils jwtUtils;
    private final TokenBlacklistService tokenBlacklistService;

    public JwtInterceptor(JwtUtils jwtUtils,TokenBlacklistService tokenBlacklistService)throws Exception {
        this.jwtUtils = jwtUtils;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authHeader = request.getHeader("Authorization");

        if(authHeader != null && authHeader.startsWith("Bearer ")){
            String token = authHeader.substring(7);

            if(tokenBlacklistService.isBlacklisted(token)){
                sendErrorResponse(response,"登出後的token已失效。");
                return false;
            }

            try{
                String account = jwtUtils.getAccountFromToken(token);
                request.setAttribute("currentUserAccount",account);  //token解析出帳號
                request.setAttribute("userToken",token);
                return true;
            }catch (Exception e){
                System.out.println("JWT解析失敗," + e.getMessage());
                sendErrorResponse(response,"無效或過期的token");
                return false;
            }
        }
        // 處理沒帶token的情況
        sendErrorResponse(response,"沒帶token");
        return false;
    }

    private void sendErrorResponse(HttpServletResponse response, String message) throws Exception{
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().write(message);
    }
}
