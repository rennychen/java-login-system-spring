package com.github.renny.loginsystem.repository;

import com.github.renny.loginsystem.user.User;

public interface UserRepository {

    User findByAccount(String userAccount); //透過帳號取得User

    boolean existsByAccount(String userAccount); //透過帳號確認User帳號是否存在

    void save(User user); //儲存User

    boolean deleteByAccount(String userAccount); //透過帳號刪除User
}
