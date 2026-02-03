package com.github.renny.loginsystem.user;

public class User {
    private String userName;  //使用者名稱
    private String passwordHash;
    private String userAccount; //登入帳號
    private int failedLoginAttempts = 0;  //計算登入錯誤次數
    private static final int MAX_FAILED_ATTEMPTS = 3; //所有使用者登入錯誤上限都是3次,所以使用static final

    public User(String userName, String passwordHash, String userAccount){
        this.userName = userName;
        this.passwordHash = passwordHash;
        this.userAccount = userAccount;
    }

    public String getUserName(){
        return userName;
    }

    public void setPassword(String passwordHash){
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
}

