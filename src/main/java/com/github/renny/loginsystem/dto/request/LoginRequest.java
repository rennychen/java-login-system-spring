package com.github.renny.loginsystem.dto.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String account;
    private String password;
}
