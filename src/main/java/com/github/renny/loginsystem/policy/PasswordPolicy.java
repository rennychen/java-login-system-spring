package com.github.renny.loginsystem.policy;

public interface PasswordPolicy {
    void validate(String password);
}
