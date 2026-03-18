package com.github.renny.loginsystem.dto.response;

public class LoginResponse {
    private boolean success;
    private String message; //給user看的內容
    private String userName;

    public LoginResponse(boolean success, String message, String userName) {
        this.success = success;
        this.message = message;
        this.userName = userName;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getUserName() {
        return userName;
    }
}
