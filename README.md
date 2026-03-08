# Java Login System with Spring Boot

本專案是一個使用 Java + Spring Boot + Maven 建置的主控台登入系統。

此版本為 Spring 架構版本，將原本純 Java 專案重構為 Spring 管理的分層架構，實作 Dependency Injection（DI）與 Component Scan。

## Introduction

本專案模擬實務中常見的帳號登入流程，包含：

 - 帳號驗證
 - 密碼加密（Hash）
 - 登入失敗次數限制
 - 帳號鎖定機制
 - Session 管理

本專案著重於：

 - 物件導向設計（OOP）
 - 分層架構（Layered Architecture）
 - Repository Pattern
 - Policy Pattern
 - 依賴注入（Dependency Injection）
 - 可抽換儲存實作（InMemory → JSON / Database）

此專案展示從「純 Java 核心邏輯」重構為「Spring Boot 架構專案」的完整演進過程。

---

## Features

- 使用者註冊
- 使用者登入驗證
- 密碼 SHA-256 加密
- 登入失敗次數累計
- 超過次數自動鎖定帳號
- 刪除帳號
- 自訂例外處理（帳號不存在、密碼錯誤、帳號鎖定）

---

 ## Architecture Overview

 ```sh
ConsoleRunner (Presentation Layer)
        ↓
AuthService (Business Logic Layer)
        ↓
UserRepository (Persistence Layer)
        ↓
InMemoryUserRepository (Current Implementation)
 ```

 ### Layer 說明

1️⃣ Presentation Layer

ConsoleRunner

 - 負責主控台互動
 - 不處理商業邏輯
 - 僅呼叫 Service

2️⃣ Service Layer

 - AuthService
 - 負責登入流程控制
 - 驗證帳號與密碼
 - 控制登入失敗與鎖定邏輯
 - 不關心資料儲存細節

3️⃣ Repository Layer

UserRepository（介面）

 - 定義資料存取行為

InMemoryUserRepository（實作）

 - 使用 HashMap 作為記憶體儲存
 - 未來可替換為 JSON / Database 實作

4️⃣ Policy Pattern

 - PasswordPolicy
 - AccountPolicy

將規則抽離為介面，未來可替換實作。

5️⃣ Security

 - PasswordEncoder
 - SHA256PasswordEncoder

密碼以 SHA-256 雜湊儲存，不保存明文。

---

## Project Structure

```sh
com.github.renny.loginsystem  
├── LoginSystemApplication.java
├── runner    
│   └── ConsoleRunner.java    
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
│   └── SHA256PasswordEncoder.java    
└── exception    
    ├── AccountNotFoundException.java    
    ├── AccountLockedException.java    
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
- SHA-256 Hash
- OOP Design Patterns

---

## Design Highlights

1️⃣ Dependency Injection

使用 Constructor Injection：

```java
public ConsoleRunner(AuthService authService, LoginSession session)
```

由 Spring Container 負責建立與管理物件。

2️⃣ Repository Pattern

商業邏輯不依賴具體實作：

```java
private final UserRepository userRepository;
```

未來可無痛替換為：

 - JSON Repository

3️⃣ Clean Separation of Concerns
| Layer        | Responsibility |
|--------------|---------------|
| Runner       | UI 互動 |
| Service      | 商業邏輯 |
| Repository   | 資料存取 |
| Policy       | 規則驗證 |
| Encoder      | 加密邏輯 |


 ## Project Evolution
 
此專案以「逐步演進」的方式進行開發，從純 Java 核心邏輯開始，逐步導入 Spring 架構與資料持久化機制，模擬實務專案的演進過程。

1️⃣ 純 Java OOP 版本

2️⃣ Spring Boot DI 版本

3️⃣ JSON 持久化版本（目前）

4️⃣ REST API 版本（進行中）-> 新增 RESTful API，將系統由 Console 應用程式轉換為 Web Backend 服務。

5️⃣ Database 版本（規劃中）-> 整合資料庫（如 MySQL），實作正式的資料持久化機制。





