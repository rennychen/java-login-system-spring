# Java Login System

使用 Java 撰寫的主控台使用者登入系統

## Introduction

這是一個以 **純 Java（不依賴任何框架）** 撰寫的主控台登入系統，

模擬實務中常見的帳號登入流程，包含帳號驗證、密碼加密、登入失敗次數限制與帳號鎖定等機制。

本專案著重於 **物件導向設計（OOP）** 與 **程式結構劃分**，

將商業邏輯與儲存機制分離，方便未來替換成資料庫或整合 Spring Boot。

---

## Features

- 使用者註冊
- 使用者登入
- 密碼加密（Hash）
- 登入失敗次數累計
- 超過次數自動鎖定帳號
- 登入成功後重置失敗次數
- 修改密碼
- 刪除帳號
- 自訂例外處理（帳號不存在、密碼錯誤、帳號鎖定）

---

## Project Structure

com.github.renny.loginsystem    
├── app    
│   └── LoginSystemApplication.java    
├── user    
│   └── User.java    
├── repository    
│   ├── UserRepository.java    
│   └── InMemoryUserRepository.java    
├── auth    
│   └── AuthService.java    
├── policy    
│   ├── AccountPolicy.java    
│   ├── DefaultAccountPolicy.java   
│   ├── PasswordPolicy.java   
│   └── DefaultPasswordPolicy.java    
├── session    
│   └── LoginSession.java   
├── encoder    
│   ├── PasswordEncoder.java    
│   └── SHA256PasswordEncoder.java    
└── exception    
    ├── AccountNotFoundException.java    
    ├── AccountLockedException.java    
    ├── InvalidAccountException.java    
    └── PasswordMismatchException.java    

---

1. 下載專案  
   

2. 編譯程式
   javac src/Main.java

3. 執行程式
   java Main

4.依照主控台選單操作（註冊 / 登入 / 修改密碼 / 刪除帳號）

---

## 使用技術






