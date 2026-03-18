package com.github.renny.loginsystem.dto.response;

public class CurrentUserResponse {
    private boolean success;
    private String message;
    private String userName;

    public CurrentUserResponse(boolean success, String message, String userName) {
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
