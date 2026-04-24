package com.github.renny.loginsystem.repository;

import com.github.renny.loginsystem.user.User;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepositoryImplementation<User,Long> {

    User findByUserAccount(String userAccount); //透過帳號取得User

    boolean existsByUserAccount(String userAccount); //透過帳號確認User帳號是否存在

//    void save(User user); //儲存User

    void deleteByUserAccount(String userAccount); //透過帳號刪除User
}
