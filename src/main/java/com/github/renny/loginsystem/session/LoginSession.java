package com.github.renny.loginsystem.session;

import com.github.renny.loginsystem.user.User;

public class LoginSession {
    private User currentUser;

    public void login(User user){
        this.currentUser = user;
    }

    public void logout(){
        this.currentUser = null;
    }

    public boolean isLoggedIn(){
        return currentUser != null;
    }

    public User getCurrentUser(){
        if(!isLoggedIn()){
            throw new IllegalStateException("尚未登入");
        }
        return currentUser;
    }
}