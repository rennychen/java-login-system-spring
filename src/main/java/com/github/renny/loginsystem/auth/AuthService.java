package com.github.renny.loginsystem.auth;

import com.github.renny.loginsystem.encoder.PasswordEncoder;
import com.github.renny.loginsystem.expection.AccountLockedException;
import com.github.renny.loginsystem.expection.AccountNotFoundException;
import com.github.renny.loginsystem.expection.PasswordMismatchException;
import com.github.renny.loginsystem.policy.AccountPolicy;
import com.github.renny.loginsystem.policy.PasswordPolicy;
import com.github.renny.loginsystem.repository.UserRepository;
import com.github.renny.loginsystem.user.User;

public class AuthService {

    private final UserRepository userRepository;
    private final PasswordPolicy passwordPolicy;
    private final PasswordEncoder passwordEncoder;
    private final AccountPolicy accountPolicy;

    public AuthService(UserRepository userRepository,PasswordPolicy passwordPolicy,PasswordEncoder passwordEncoder,AccountPolicy accountPolicy) {
        this.userRepository = userRepository;
        this.passwordPolicy = passwordPolicy;
        this.passwordEncoder = passwordEncoder;
        this.accountPolicy = accountPolicy;
    }

    public void register(String account, String userName, String password) {
        checkAccountNotExists(account); //確認帳號是否存在
        checkAccountPolicyCorrect(account); //確認帳號是否符合設置規範
        checkPasswordPolicyCorrect(password); //確認密碼設置是否符合規範
        String hashedPassword = passwordEncoder.encode(password); //密碼改成Hash


        User user = new User(userName, hashedPassword, account); //使用者密碼改為hash儲存
        userRepository.save(user);
    }

    private void checkAccountNotExists(String account){
        if(userRepository.existsByAccount(account)){
            throw new RuntimeException("帳號已存在");
        }
    }

    private void checkPasswordPolicyCorrect(String password){
        passwordPolicy.validate(password);
    }

    private void checkAccountPolicyCorrect(String account){ accountPolicy.validate(account); }

    public User login(String account,String password){
        User user = userRepository.findByAccount(account);
        if(user == null){
            throw new AccountNotFoundException("帳號不存在");
        }

        if(user.isLocked()){
            throw new AccountLockedException("登入錯誤超過3次，帳號已鎖定");
        }

        boolean passwordMatch = passwordEncoder.matches(password,user.getPasswordHash());

        if(!passwordMatch){ //登入失敗增加登入失敗次數
            user.increaseFailedLoginAttempts();
            throw new PasswordMismatchException("密碼錯誤!");
        }

        user.resetFailedLoginAttempts();
        return user;
    }
}
