package com.github.renny.loginsystem.controller;

import com.github.renny.loginsystem.auth.AuthService;
import com.github.renny.loginsystem.dto.request.LoginRequest;
import com.github.renny.loginsystem.dto.request.PasswordChangeRequest;
import com.github.renny.loginsystem.dto.request.RegisterRequest;
import com.github.renny.loginsystem.expection.AccountLockedException;
import com.github.renny.loginsystem.expection.AccountNotFoundException;
import com.github.renny.loginsystem.expection.InvalidAccountException;
import com.github.renny.loginsystem.expection.PasswordMismatchException;
import com.github.renny.loginsystem.user.User;
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
    public String register(@RequestBody RegisterRequest request){
        try{
            authService.register(request.getAccount(),request.getUserName(),request.getPassword());
            return "註冊成功。";
        } catch (RuntimeException e) {
            return "註冊失敗," + e.getMessage();
        }

    }

    //登入
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request){
        try{
            User user = authService.login(request.getAccount(),request.getPassword());
            return "登入成功!歡迎回來," + user.getUserName();
        }catch (AccountNotFoundException | AccountLockedException | PasswordMismatchException e){
            return "錯誤!" + e.getMessage();
        }
    }

    //顯示目前登入身分
    @GetMapping("/current")
    public String showCurrentUser(){
        try{
            return "目前使用者為:" + authService.showCurrentUser();
        }catch (IllegalStateException e){
            return "錯誤," + e.getMessage();
        }
    }

    //登出
    @PostMapping("/logout")
    public String logout(){
        try{
            authService.logout();
            return "登出成功。";
        }catch (InvalidAccountException e){
            return "錯誤," + e.getMessage();
        }

    }

    //刪除帳號
    @DeleteMapping("account")
    public String deleteUserAccount(){
        try{
            if(authService.deleteUserAccount()){
                        return "刪除帳號成功";
            }
            return "取消刪除";
        }catch (IllegalStateException e){
            return "錯誤," + e.getMessage();
        }
    }

    //改密碼
    @PatchMapping("/password")
    public String changePassword(@RequestBody PasswordChangeRequest request){
        try{
            authService.changePassword(request.getOldPassword(),request.getNewPassword(),request.getNewPassword2());
            return "更改密碼完成!請用新密碼重新登入";
        }catch(RuntimeException e){
            return "錯誤!" + e.getMessage();
        }
    }
}
