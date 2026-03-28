package com.github.renny.loginsystem.encoder;

import org.springframework.stereotype.Component;

@Component("transitioningPasswordEncoderImpl")
public class TransitioningPasswordEncoderImpl implements PasswordEncoder{

    private final BCryptPasswordEncoderImpl bCryptPasswordEncoder;
    private final SHA256PasswordEncoder sha256PasswordEncoder;

    public TransitioningPasswordEncoderImpl(BCryptPasswordEncoderImpl bCryptPasswordEncoder,SHA256PasswordEncoder sha256PasswordEncoder){
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.sha256PasswordEncoder = sha256PasswordEncoder;
    }

    @Override
    public String encode(String rawPassword) {
        return bCryptPasswordEncoder.encode(rawPassword);
        //一律使用新的雜湊
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        if(encodedPassword == null || rawPassword == null){
            return false;
        }

        if(encodedPassword.startsWith("$2")){
            return bCryptPasswordEncoder.matches(rawPassword,encodedPassword);
        }

        return sha256PasswordEncoder.matches(rawPassword,encodedPassword);
    }

    @Override
    public boolean  upgradeEncoding(String encodedPassword){
        return !encodedPassword.startsWith("$2");
    }
}
