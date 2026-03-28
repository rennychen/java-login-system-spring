package com.github.renny.loginsystem.auth;

import com.github.renny.loginsystem.encoder.PasswordEncoder;
import com.github.renny.loginsystem.expection.AccountLockedException;
import com.github.renny.loginsystem.expection.AccountNotFoundException;
import com.github.renny.loginsystem.expection.InvalidAccountException;
import com.github.renny.loginsystem.expection.PasswordMismatchException;
import com.github.renny.loginsystem.policy.AccountPolicy;
import com.github.renny.loginsystem.policy.PasswordPolicy;
import com.github.renny.loginsystem.repository.UserRepository;
import com.github.renny.loginsystem.session.LoginSession;
import com.github.renny.loginsystem.user.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordPolicy passwordPolicy;
    private final PasswordEncoder passwordEncoder;
    private final AccountPolicy accountPolicy;
    private final LoginSession loginSession;

    public AuthService(@Qualifier("jsonUserRepository") UserRepository userRepository,
                       PasswordPolicy passwordPolicy,
                       @Qualifier("transitioningPasswordEncoderImpl") PasswordEncoder passwordEncoder,
                       AccountPolicy accountPolicy,
                       LoginSession loginSession) {
        this.userRepository = userRepository;
        this.passwordPolicy = passwordPolicy;
        this.passwordEncoder = passwordEncoder;
        this.accountPolicy = accountPolicy;
        this.loginSession = loginSession;
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
            userRepository.save(user);
            throw new PasswordMismatchException("密碼錯誤!");
        }


        if(passwordEncoder.upgradeEncoding(user.getPasswordHash())){
            String newHashedPassword = passwordEncoder.encode(password);
            user.setPasswordHash(newHashedPassword);
        }

        loginSession.login(user);
        user.resetFailedLoginAttempts();
        userRepository.save(user);
        return user;
    }

    public void logout(){
        if(!loginSession.isLoggedIn()){
            throw new InvalidAccountException("尚未登入。");
        }
        loginSession.logout();
    }

    public String showCurrentUser(){
        if(!loginSession.isLoggedIn()){
            throw new IllegalStateException("尚未登入!");
        }
        return loginSession.getCurrentUser().getUserName();

    }

    public boolean deleteUserAccount(){
        if(!loginSession.isLoggedIn()){
            throw new IllegalStateException("尚未登入!");
        }

        String account = loginSession.getCurrentUser().getUserAccount();
        boolean result = userRepository.deleteByAccount(account);
        loginSession.logout();
        return result;

    }

    public void changePassword(String oldPassword,String newPassword,String newPassword2){

        User user = loginSession.getCurrentUser();
        String oldPasswordHash = passwordEncoder.encode(oldPassword);
        if(!oldPasswordHash.equals(user.getPasswordHash())){
            throw new PasswordMismatchException("原始密碼輸入不符!");
        }

        if(!newPassword.equals(newPassword2)){
            throw new PasswordMismatchException("輸入新密碼不相同!");
        }

        checkPasswordPolicyCorrect(newPassword); //確認密碼設置是否符合規範
        String newPasswordHash = passwordEncoder.encode(newPassword); //密碼改成Hash
        user.setPasswordHash(newPasswordHash);
        userRepository.save(user);
        loginSession.logout();

    }
}
