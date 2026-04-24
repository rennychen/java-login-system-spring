package com.github.renny.loginsystem.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//@JsonIgnoreProperties(ignoreUnknown = true)  //json資料庫使用
@Entity
@Table( name = "users" )
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column( nullable = false )
    private String userName;  //使用者名稱
    @Column( nullable = false )
    private String passwordHash;
    @Column( nullable = false, unique = true )
    private String userAccount;
    @Column(nullable = false)//登入帳號
    private int failedLoginAttempts = 0;  //計算登入錯誤次數
    private static final int MAX_FAILED_ATTEMPTS = 3; //所有使用者登入錯誤上限都是3次,所以使用static final

    public User() {} //無參數建構子

    public User(String userName, String passwordHash, String userAccount){
        this.userName = userName;
        this.passwordHash = passwordHash;
        this.userAccount = userAccount;
    }

    public String getUserName(){
        return userName;
    }

    public void setPasswordHash(String passwordHash){
        this.passwordHash = passwordHash;
    }

    public String getPasswordHash(){ return passwordHash; }

    public String getUserAccount(){
        return userAccount;
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

    private void setUserName(String userName){
        this.userName = userName;
    }

    private void setUserAccount(String userAccount){
        this.userAccount = userAccount;
    }

    public int getFailedLoginAttempts(){
        return failedLoginAttempts;
    }

    private void setFailedLoginAttempts(int failedLoginAttempts){
        this.failedLoginAttempts = failedLoginAttempts;
    }

    private Long getId() { return id; }

    private void setId() { this.id = id; }
}

