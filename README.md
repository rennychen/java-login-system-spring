

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
│   ├── PasswordPolicy.java
│   └── DefaultPasswordPolicy.java
├── encoder
│   ├── PasswordEncoder.java
│   └── SHA256PasswordEncoder.java
└── exception
    ├── AccountNotFoundException.java
    ├── AccountLockedException.java
    └── PasswordMismatchException.java
