package com.github.renny.loginsystem.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private String userName;  //使用者名稱
    private String passwordHash;
    private String userAccount; //登入帳號
    private int failedLoginAttempts = 0;  //計算登入錯誤次數
    private static final int MAX_FAILED_ATTEMPTS = 3; //所有使用者登入錯誤上限都是3次,所以使用static final

    public User() {} //給Jackson使用

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

    @JsonIgnore
    public boolean isLocked(){
        return failedLoginAttempts >= MAX_FAILED_ATTEMPTS ;
    }

    @JsonProperty
    private void setUserName(String userName){
        this.userName = userName;
    }

    @JsonProperty
    private void setUserAccount(String userAccount){
        this.userAccount = userAccount;
    }


    public int getFailedLoginAttempts(){
        return failedLoginAttempts;
    }

    @JsonProperty
    private void setFailedLoginAttempts(int failedLoginAttempts){
        this.failedLoginAttempts = failedLoginAttempts;
    }
}

