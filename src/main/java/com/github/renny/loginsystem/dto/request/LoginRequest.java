package com.github.renny.loginsystem.dto.request;

public class LoginRequest {
    private String account;
    private String password;

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
