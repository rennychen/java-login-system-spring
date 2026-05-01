package com.github.renny.loginsystem.controller;

import com.github.renny.loginsystem.auth.AuthService;
import com.github.renny.loginsystem.dto.request.LoginRequest;
import com.github.renny.loginsystem.dto.request.ChangePasswordRequest;
import com.github.renny.loginsystem.dto.request.RegisterRequest;
import com.github.renny.loginsystem.dto.response.ApiResponse;
import com.github.renny.loginsystem.dto.response.CurrentUserResponse;
import com.github.renny.loginsystem.dto.response.LoginResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag( name = "Auth", description = "帳號動作相關 API" )
public class AuthController {

    private final AuthService authService;

    //註冊
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@RequestBody RegisterRequest request){
        authService.register(request.getAccount(),request.getUserName(),request.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("註冊成功",null));
    }

    //登入
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request){
        LoginResponse successData = authService.login(request.getAccount(),request.getPassword());
        return ResponseEntity.ok(ApiResponse.success("登入成功" ,successData));
    }

    //顯示目前登入身分
    @GetMapping("/current")
    public ResponseEntity<ApiResponse<CurrentUserResponse>> showCurrentUser(HttpServletRequest request){
        String account = (String) request.getAttribute("currentUserAccount");
        CurrentUserResponse successData = new CurrentUserResponse(authService.showCurrentUser(account));
        return ResponseEntity.ok(ApiResponse.success("目前使用者為",successData));
    }

    //登出
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(HttpServletRequest request){
        String token = (String)request.getAttribute("userToken");
        authService.logout(token);
        return ResponseEntity.ok(ApiResponse.success("登出成功!",null));
    }

    //刪除帳號
    @DeleteMapping("/account")
    public ResponseEntity<ApiResponse<Void>> deleteUserAccount(HttpServletRequest request){
        String account = (String) request.getAttribute("currentUserAccount");
        authService.deleteUserAccount(account);
        return ResponseEntity.ok(ApiResponse.success("刪除帳號成功!",null));
    }

    //改密碼
    @PatchMapping("/password")
    public ResponseEntity<ApiResponse<Void>> changePassword(@RequestBody ChangePasswordRequest request,HttpServletRequest httpServletRequest){
        String account = (String)httpServletRequest.getAttribute("currentUserAccount");
        String token = (String)httpServletRequest.getAttribute("userToken");
        authService.changePassword(request.getOldPassword(),request.getNewPassword(),request.getNewPassword2(),account,token);
        return ResponseEntity.ok(ApiResponse.success("更改密碼完成!請用新密碼重新登入",null));
    }
}
