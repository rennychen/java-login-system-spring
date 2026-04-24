package com.github.renny.loginsystem.controller;


import com.github.renny.loginsystem.security.JwtUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    private final JwtUtils jwtUtils;

    public TestController(JwtUtils jwtUtils){
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/token")
    public String getToken(){
        return jwtUtils.createToken("testAccount");
    }
}
