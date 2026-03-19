package com.github.renny.loginsystem.dto.response;

public class CurrentUserResponse {
    private String userName;

    public CurrentUserResponse(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
