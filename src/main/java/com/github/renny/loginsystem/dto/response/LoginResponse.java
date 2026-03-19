package com.github.renny.loginsystem.dto.response;

public class LoginResponse {
    private String userName;

    public LoginResponse(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
