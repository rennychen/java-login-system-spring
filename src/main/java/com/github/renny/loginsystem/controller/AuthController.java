package com.github.renny.loginsystem.controller;

import com.github.renny.loginsystem.auth.AuthService;
import com.github.renny.loginsystem.dto.request.LoginRequest;
import com.github.renny.loginsystem.dto.request.ChangePasswordRequest;
import com.github.renny.loginsystem.dto.request.RegisterRequest;
import com.github.renny.loginsystem.dto.response.ApiResponse;
import com.github.renny.loginsystem.dto.response.CurrentUserResponse;
import com.github.renny.loginsystem.dto.response.LoginResponse;
import com.github.renny.loginsystem.expection.AccountLockedException;
import com.github.renny.loginsystem.expection.AccountNotFoundException;
import com.github.renny.loginsystem.expection.InvalidAccountException;
import com.github.renny.loginsystem.expection.PasswordMismatchException;
import com.github.renny.loginsystem.user.User;
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
public class AuthController {

    private final  AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    //註冊
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@RequestBody RegisterRequest request){
        try{
            authService.register(request.getAccount(),request.getUserName(),request.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("註冊成功",null));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("註冊失敗," + e.getMessage()));
        }

    }

    //登入
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request){
        try{
            User user = authService.login(request.getAccount(),request.getPassword());
            LoginResponse successData = new LoginResponse(user.getUserName());
            return ResponseEntity.ok(ApiResponse.success("登入成功,歡迎回來",successData));
        }catch (AccountNotFoundException | AccountLockedException | PasswordMismatchException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("錯誤!" + e.getMessage()));
        }
    }

    //顯示目前登入身分
    @GetMapping("/current")
    public ResponseEntity<ApiResponse<CurrentUserResponse>> showCurrentUser(){
        try{
            CurrentUserResponse successData = new CurrentUserResponse(authService.showCurrentUser());
            return ResponseEntity.ok(ApiResponse.success("目前使用者為",successData));
        }catch (IllegalStateException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("錯誤!" + e.getMessage()));
        }
    }

    //登出
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(){
        try{
            authService.logout();
            return ResponseEntity.ok(ApiResponse.success("登出成功!",null));
        }catch (InvalidAccountException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("錯誤," + e.getMessage()));
        }
    }

    //刪除帳號
    @DeleteMapping("account")
    public ResponseEntity<ApiResponse<Void>> deleteUserAccount(){
        try{
            authService.deleteUserAccount();
            return ResponseEntity.ok(ApiResponse.success("刪除帳號成功!",null));
        }catch (IllegalStateException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("錯誤," + e.getMessage()));
        }
    }

    //改密碼
    @PatchMapping("/password")
    public ResponseEntity<ApiResponse<Void>> changePassword(@RequestBody ChangePasswordRequest request){
        try{
            authService.changePassword(request.getOldPassword(),request.getNewPassword(),request.getNewPassword2());
            return ResponseEntity.ok(ApiResponse.success("更改密碼完成!請用新密碼重新登入",null));
        }catch(RuntimeException e){
            return ResponseEntity.badRequest().body(ApiResponse.error("錯誤!" + e.getMessage()));
        }
    }
}
