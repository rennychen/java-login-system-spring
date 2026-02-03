package com.github.renny.loginsystem.repository;

import com.github.renny.loginsystem.user.User;

import java.util.Map;
import java.util.HashMap;

public class InMemoryUserRepository implements UserRepository{

    Map<String, User> data = new HashMap<>();

    @Override
    public User findByAccount(String userAccount){
        return data.get(userAccount);
    }

    @Override
    public boolean existsByAccount(String userAccount){
        return data.get(userAccount) != null; // 或是 return data.containsKey(userAccount);
    }

    @Override
    public void save(User user){
        data.put(user.getUserAccount(),user);
    }

    @Override
    public boolean deleteByAccount(String userAccount){
        if(!data.containsKey(userAccount)){
            return false;
        }
        data.remove(userAccount);
        return true;
    }

}
