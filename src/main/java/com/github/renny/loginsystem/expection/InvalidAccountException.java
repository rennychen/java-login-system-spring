package com.github.renny.loginsystem.expection;

public class InvalidAccountException extends RuntimeException {
    public InvalidAccountException(String message){
        super(message);
    }
}

