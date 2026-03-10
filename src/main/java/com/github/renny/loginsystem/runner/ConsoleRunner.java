package com.github.renny.loginsystem.runner;

import com.github.renny.loginsystem.auth.AuthService;
import com.github.renny.loginsystem.expection.AccountLockedException;
import com.github.renny.loginsystem.expection.AccountNotFoundException;
import com.github.renny.loginsystem.expection.InvalidAccountException;
import com.github.renny.loginsystem.expection.PasswordMismatchException;

import com.github.renny.loginsystem.user.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private final AuthService authService;

    public ConsoleRunner(AuthService authService) {
        this.authService = authService;
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
            System.out.println("5->刪除帳號");
            System.out.println("6->更改密碼");
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
                case "5":
                    deleteUserAccount(scan);
                    break;
                case "6":
                    setUserPassword(scan);
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
            System.out.println("登入成功!歡迎回來," + user.getUserName());
        }catch (AccountNotFoundException | AccountLockedException | PasswordMismatchException e){
            System.out.println(e.getMessage());
        }
    }

    //顯示目前登入身分
    private void showCurrentUser(){
        try{
            System.out.println("目前使用者為:" + authService.showCurrentUser());
        }catch (IllegalStateException e){
            System.out.println("錯誤," + e.getMessage());
        }
    }

    //登出
    private void logout(){
        try{
            authService.logout();
            System.out.println("登出成功。");
        }catch (InvalidAccountException e){
            System.out.println("錯誤," + e.getMessage());
        }

    }

    //刪除帳號
    private void deleteUserAccount(Scanner scan){

        try{
            System.out.println("請確認是否刪除當前登入帳號?");
            System.out.println("1->確定刪除");
            System.out.println("0->取消刪除");
            String num = scan.nextLine();
            switch (num){
                case "1":
                    if(authService.deleteUserAccount()){
                        System.out.println("刪除帳號成功");
                    }
                    break;
                case  "0":
                    System.out.println("已取消刪除帳號");
                    break;
                default:
                    System.out.println("請輸入正確的數字!");
                    break;
            }
        }catch (IllegalStateException e){
            System.out.println("錯誤," + e.getMessage());
        }
    }

    //改密碼
    private void setUserPassword(Scanner scan){

        try{
            String userName = authService.showCurrentUser();
            System.out.println(userName + "您好,目前使用功能為更改密碼。");
            System.out.print("請輸入舊密碼:");
            String oldPassword = scan.nextLine();
            System.out.print("請輸入新密碼:");
            String newPassword = scan.nextLine();
            System.out.print("再次確認新密碼:");
            String newPassword2 = scan.nextLine();
            authService.changePassword(oldPassword,newPassword,newPassword2);

            System.out.println("更改密碼完成!請用新密碼重新登入");


        }catch(RuntimeException e){
            System.out.println("錯誤!" + e.getMessage());
        }
    }

}



