package com.github.renny.loginsystem.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table( name = "users" )
@Slf4j
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( nullable = false )
    private String userName;  //使用者名稱

    @Column( nullable = false )
    @Setter
    private String passwordHash;

    @Column( nullable = false, unique = true )
    private String userAccount;

    @Column(nullable = false)//登入帳號
    private int failedLoginAttempts = 0;  //計算登入錯誤次數
    private static final int MAX_FAILED_ATTEMPTS = 3; //所有使用者登入錯誤上限都是3次,所以使用static final

    public User(String userName, String passwordHash, String userAccount){
        this.userName = userName;
        this.passwordHash = passwordHash;
        this.userAccount = userAccount;
    }


    public void resetFailedLoginAttempts(){
        failedLoginAttempts = 0;
    }

    public void increaseFailedLoginAttempts(){
        failedLoginAttempts++;
    }

    public boolean isLocked(){
        return failedLoginAttempts >= MAX_FAILED_ATTEMPTS ;
    }

}

