package com.github.renny.loginsystem.encoder;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component("bCryptPasswordEncoderImpl")
public class BCryptPasswordEncoderImpl implements PasswordEncoder {

    private  final BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();

    @Override
    public String encode(String rawPassword){
        return bCrypt.encode(rawPassword); //自動產生鹽值+雜湊後的字串
    }

    @Override
    public boolean matches(String rawPassword,String encodedPassword){
        return bCrypt.matches(rawPassword,encodedPassword);
    }
}
