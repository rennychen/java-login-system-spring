package com.github.renny.loginsystem.expection;

public class AccountLockedException extends RuntimeException {
    public AccountLockedException(String message){
        super(message);
    }
}
