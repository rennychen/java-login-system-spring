package com.github.renny.loginsystem.dto.request;

import lombok.Data;

@Data
public class RegisterRequest {
    private String account;
    private String userName;
    private String password;
}
