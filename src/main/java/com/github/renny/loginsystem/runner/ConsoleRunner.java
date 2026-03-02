package com.github.renny.loginsystem.runner;

import com.github.renny.loginsystem.auth.AuthService;
import com.github.renny.loginsystem.expection.AccountLockedException;
import com.github.renny.loginsystem.expection.AccountNotFoundException;
import com.github.renny.loginsystem.expection.PasswordMismatchException;

import com.github.renny.loginsystem.session.LoginSession;
import com.github.renny.loginsystem.user.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private final AuthService authService;
    private final LoginSession session;

    public ConsoleRunner(AuthService authService,LoginSession session) {
        this.authService = authService;
        this.session = session;
    }

    @Override
    public void run(String... args) throws Exception {

        Scanner scan = new Scanner(System.in);

        while (true){
            System.out.println("====MENU====");
            System.out.println("1->註冊帳號");
            System.out.println("2->登入帳號");
            System.out.println("3->顯示目前登入身分");
            System.out.println("4->登出");
            System.out.println("0->結束使用");
            System.out.print("選擇使用功能:");

            String num = scan.nextLine();
            switch (num){
                case "1":
                    register(scan);
                    break;
                case "2":
                    login(scan);
                    break;
                case "3":
                    showCurrentUser();
                    break;
                case "4":
                    logout();
                    break;
                case "0":
                    System.out.println("謝謝使用本系統。");
                    return;
                default:
                    System.out.println("請輸入正確的數字!");
                    break;
            }
        }

    }



//註冊
    private void register(Scanner scan){
        try{
            System.out.println("帳號設置規則:帳號長度為6-20字,只能包含英文字母與數字,且不得使用數字做開頭。");
            System.out.print("請輸入帳號:");
            String account = scan.nextLine();

            System.out.println("密碼設置規則:密碼長度8-16位數，且需包含至少1個英文大寫,1個英文小寫及數字組成。");
            System.out.print("請輸入密碼:");
            String password = scan.nextLine();

            System.out.print("請輸入使用者名稱:");
            String userName = scan.nextLine();

            authService.register(account,userName,password);
            System.out.println("註冊成功。");
        } catch (RuntimeException e) {
            System.out.println("註冊失敗," + e.getMessage());
        }

    }

//登入
    private void login(Scanner scan){
        try{
            System.out.print("請輸入帳號:");
            String account = scan.nextLine();

            System.out.print("請輸入密碼:");
            String password = scan.nextLine();

            User user = authService.login(account,password);
            session.login(user);
            System.out.println("登入成功!歡迎回來," + user.getUserName());
        }catch (AccountNotFoundException e){
            System.out.println(e.getMessage());
        }catch (AccountLockedException e){
            System.out.println(e.getMessage());
        }catch (PasswordMismatchException e){
            System.out.println(e.getMessage());
        }

    }

    //顯示目前登入身分
    private void showCurrentUser(){
        try{
            User user = session.getCurrentUser();
            System.out.println("目前使用者為:" + user.getUserName());
        }catch (IllegalStateException e){
            System.out.println("錯誤," + e.getMessage());
        }
    }

    //登出
    private void logout(){
        if(session.isLoggedIn()){
            session.logout();
            System.out.println("登出成功!");
        }else{
            System.out.println("尚未登入。");
        }

    }

}



