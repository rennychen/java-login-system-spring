package com.github.renny.loginsystem.controller;

import com.github.renny.loginsystem.auth.AuthService;
import com.github.renny.loginsystem.dto.request.LoginRequest;
import com.github.renny.loginsystem.dto.request.ChangePasswordRequest;
import com.github.renny.loginsystem.dto.request.RegisterRequest;
import com.github.renny.loginsystem.dto.response.ChangePasswordResponse;
import com.github.renny.loginsystem.dto.response.CurrentUserResponse;
import com.github.renny.loginsystem.dto.response.DeleteAccountResponse;
import com.github.renny.loginsystem.dto.response.LoginResponse;
import com.github.renny.loginsystem.dto.response.LogoutResponse;
import com.github.renny.loginsystem.dto.response.RegisterResponse;
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
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request){
        try{
            authService.register(request.getAccount(),request.getUserName(),request.getPassword());
            RegisterResponse successData = new RegisterResponse(true,"註冊成功");
            return ResponseEntity.ok(successData);
        } catch (RuntimeException e) {
            RegisterResponse errorData = new RegisterResponse(false,"註冊失敗," + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorData);
        }

    }

    //登入
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        try{
            User user = authService.login(request.getAccount(),request.getPassword());
            LoginResponse successData = new LoginResponse(true,"登入成功!歡迎回來,",user.getUserName());
            return ResponseEntity.ok(successData);
        }catch (AccountNotFoundException | AccountLockedException | PasswordMismatchException e){
            LoginResponse errorData = new LoginResponse(false,"錯誤!" + e.getMessage(),null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorData);
        }
    }

    //顯示目前登入身分
    @GetMapping("/current")
    public ResponseEntity<CurrentUserResponse> showCurrentUser(){
        try{
            CurrentUserResponse successData = new CurrentUserResponse(true,"目前使用者為" , authService.showCurrentUser());
            return ResponseEntity.ok(successData);
        }catch (IllegalStateException e){
            CurrentUserResponse errorData = new CurrentUserResponse(false,"錯誤," + e.getMessage(),null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorData);
        }
    }

    //登出
    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logout(){
        try{
            authService.logout();
            LogoutResponse successData = new LogoutResponse(true,"登出成功!");
            return ResponseEntity.ok(successData);
        }catch (InvalidAccountException e){
            LogoutResponse errorData = new LogoutResponse(false,"錯誤," + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorData);
        }
    }

    //刪除帳號
    @DeleteMapping("account")
    public ResponseEntity<DeleteAccountResponse> deleteUserAccount(){
        try{
            if(authService.deleteUserAccount()){
                DeleteAccountResponse successData = new DeleteAccountResponse(true,"刪除帳號成功!");
                return ResponseEntity.ok(successData);
            }
            //用戶取消刪除帳號
            DeleteAccountResponse errorData = new DeleteAccountResponse(false,"取消刪除");
            return ResponseEntity.ok(errorData);
        }catch (IllegalStateException e){
            DeleteAccountResponse errorData = new DeleteAccountResponse(false,"錯誤," + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorData);
        }
    }

    //改密碼
    @PatchMapping("/password")
    public ResponseEntity<ChangePasswordResponse> changePassword(@RequestBody ChangePasswordRequest request){
        try{
            authService.changePassword(request.getOldPassword(),request.getNewPassword(),request.getNewPassword2());
            ChangePasswordResponse successData = new ChangePasswordResponse(true,"更改密碼完成!請用新密碼重新登入");
            return ResponseEntity.ok(successData);
        }catch(RuntimeException e){
            ChangePasswordResponse errorData = new ChangePasswordResponse(false,"錯誤!" + e.getMessage());
            return ResponseEntity.badRequest().body(errorData);
        }
    }
}
