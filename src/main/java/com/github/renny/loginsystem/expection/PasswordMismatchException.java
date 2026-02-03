package com.github.renny.loginsystem.expection;

public class PasswordMismatchException extends RuntimeException {
    public PasswordMismatchException(String message){
        super(message);
    }
}
