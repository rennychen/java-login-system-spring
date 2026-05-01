package com.github.renny.loginsystem.expection;

import com.github.renny.loginsystem.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExcpectionHandler {

    @ExceptionHandler(AccountLockedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccountLocked(AccountLockedException e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.error("登入失敗," + e.getMessage()));
    }

    @ExceptionHandler({AccountNotFoundException.class,InvalidAccountException.class,PasswordMismatchException.class})
    public ResponseEntity<ApiResponse<Void>> handleAuthFail(RuntimeException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("錯誤!" + e.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Void>> handleAll(RuntimeException e){
        log.error("發生錯誤 ",e);
        return ResponseEntity.badRequest().body(ApiResponse.error("錯誤!" + e.getMessage()));
    }
}
