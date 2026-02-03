package com.github.renny.loginsystem.policy;

public class DefaultPasswordPolicy implements PasswordPolicy {
    @Override
    public void validate(String password){
        if(!password.matches(".{8,16}")){
            throw new RuntimeException("密碼長度需設置8-16位!");
        }

        if(!password.matches(".*\\d.*")){
            throw new RuntimeException("密碼須包含至少1個數字!");
        }

        if(!password.matches(".*[A-Z].*")){
            throw new RuntimeException("密碼須包含至少1個英文大寫!");
        }

        if(!password.matches(".*[a-z].*")){
            throw new RuntimeException("密碼須包含至少1個英文小寫!");
        }

        if(password.matches(".*\\s.*")){
            throw new RuntimeException("密碼不得包含空白!");
        }
    }
}
