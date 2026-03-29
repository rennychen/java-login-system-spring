# Java Login System (Spring Boot & REST API)

這是一個基於 Java Spring Boot 開發的後端登入系統。

專案展示了從傳統 Console 應用程式演進至 REST API 架構的過程，並嚴格遵循分層架構設計。


## Introduction

本專案模擬實務中常見的帳號登入流程，包含：

 - 帳號驗證
 - 密碼加密（Hash）
 - 登入失敗次數限制
 - 帳號鎖定機制
 - Session 管理

本專案著重於：

 - 物件導向設計 (OOP) 與 分層架構 (Layered Architecture)
 - Repository & Policy ：實作可抽換的儲存機制與規則驗證
 - REST 轉型：從「純 Java 核心」重構為「Web API 服務」的完整演進

---

## Features

- 使用者帳號管理：註冊、登入、刪除帳號、更改密碼。
- 安全機制：使用 BCrypt 強力雜湊加密，不保存明文密碼。
- 帳號保護：登入失敗次數累計，超過次數自動鎖定帳號。
- 全域異常處理：自定義例外（如 AccountLockedException）並統一回傳錯誤訊息。

---

 ## Architecture Overview

 ```sh
[ Web Frontend / Postman ]  <-->  [ AuthController (REST API) ]
                                            ↓
[ ConsoleRunner (UI Layer) ]  --> [ AuthService (Business Logic) ]
                                            ↓
                                  [ UserRepository (Persistence Interface) ]
                                            ↓
                                  [ JsonUserRepository / InMemory (Current) ]

 ```

 ### Layer 說明

1️⃣ Controller Layer

 - AuthController: 處理 REST API 請求，回傳 JSON 格式資料。

2️⃣ Service Layer

 - AuthService: 負責登入流程控制、驗證邏輯與帳號鎖定判斷。

3️⃣ Repository Layer

 - UserRepository (Interface): 定義資料存取行為，實現與具體儲存技術解耦。

4️⃣ Policy & Security

 - Policy Pattern: 將密碼與帳號規則抽離為介面，方便未來更換驗證邏輯。
 - PasswordEncoder: 採用 BCrypt 雜湊儲存，確保資料安全性。

---

## Project Structure

```sh
com.github.renny.loginsystem  
├── LoginSystemApplication.java
├── controller
│   └── AuthController.java
├── dto
│   ├── request
│   │   ├──ChangePasswordRequest.java
│   │   ├──LoginRequest.java
│   │   └──RegisterRequest.java
│   └── response
│       ├──ApiResponse.java
│       ├──CurrentUserResponse.java
│       └──LoginResponse.java  
├── user    
│   └── User.java    
├── repository    
│   ├── UserRepository.java
│   ├── JsonUserRepository.java
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
│   ├── BCryptPasswordEncoderImpl.java
│   ├── TransitioningPasswordEncoder.java
│   └── SHA256PasswordEncoder.java    
└── exception    
    ├── AccountNotFoundException.java    
    ├── AccountLockedException.java
    ├── GlobalExcpectionHandler.java
    ├── InvalidAccountException.java    
    └── PasswordMismatchException.java    
```

---

## How to Run

直接在 IDE 執行：

LoginSystemApplication.java

## How to Run (Maven)

```bash
mvn clean package
java -jar target/login-system-1.0-SNAPSHOT.jar

```

---

## Tech Stack

- Java
- Spring Boot
- Maven
- BCrypt Encoding

---

 ## Project Evolution
 
此專案以「逐步演進」的方式進行開發，從純 Java 核心邏輯開始，逐步導入 Spring 架構與資料持久化機制，模擬實務專案的演進過程。

1️⃣ 純 Java OOP 版本：建立基礎物件模型。

2️⃣ Spring Boot DI：版本導入 Spring 框架進行依賴管理。

3️⃣ JSON 持久化版本：實作檔案儲存。

4️⃣ REST API 版本（目前）：新增 Controller，支援 Web Backend 串接。

5️⃣ Database 版本（規劃中）-> 整合資料庫（如 MySQL），實作正式的資料持久化機制。





