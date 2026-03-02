package com.github.renny.loginsystem;
import java.util.Scanner;

import com.github.renny.loginsystem.auth.AuthService;
import com.github.renny.loginsystem.encoder.PasswordEncoder;
import com.github.renny.loginsystem.encoder.SHA256PasswordEncoder;
import com.github.renny.loginsystem.expection.AccountLockedException;
import com.github.renny.loginsystem.expection.AccountNotFoundException;
import com.github.renny.loginsystem.expection.PasswordMismatchException;
import com.github.renny.loginsystem.policy.AccountPolicy;
import com.github.renny.loginsystem.policy.DefaultAccountPolicy;
import com.github.renny.loginsystem.policy.DefaultPasswordPolicy;
import com.github.renny.loginsystem.policy.PasswordPolicy;
import com.github.renny.loginsystem.repository.InMemoryUserRepository;
import com.github.renny.loginsystem.repository.UserRepository;
import com.github.renny.loginsystem.session.LoginSession;
import com.github.renny.loginsystem.user.User;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class LoginSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(LoginSystemApplication.class, args);

    }
}

