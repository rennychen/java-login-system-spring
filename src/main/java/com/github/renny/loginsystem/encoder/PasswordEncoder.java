package com.github.renny.loginsystem.encoder;

public interface PasswordEncoder {
    String encode(String rawPassword);

    boolean matches(String rawPassword,String encodedPassword);
}
