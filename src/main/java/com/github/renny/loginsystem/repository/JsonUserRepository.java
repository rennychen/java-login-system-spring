package com.github.renny.loginsystem.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.renny.loginsystem.user.User;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class JsonUserRepository implements UserRepository{
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final File file = new File("users.json");

    private Map<String,User> loadData(){
        try{
            if(!file.exists()  || file.length() == 0){
                return new HashMap<>();
            }
            return objectMapper.readValue(file, new TypeReference<Map<String, User>>() {});
        }catch(IOException e){
            throw new RuntimeException("讀取JSON檔案失敗!",e);
        }
    }

    private void saveData(Map<String,User> data){
        try{
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file,data);
        }catch(IOException e){
            throw new RuntimeException("寫入JSON檔案失敗!",e);
        }
    }

    @Override
    public User findByAccount(String userAccount){
        return loadData().get(userAccount);
    }

    @Override
    public boolean existsByAccount(String userAccount){
        return loadData().get(userAccount) != null;
    }

    @Override
    public void save(User user){
        Map<String, User> data = loadData();
        data.put(user.getUserAccount(),user);
        saveData(data);
    }

    @Override
    public boolean deleteByAccount(String userAccount){
        Map<String, User> data = loadData();
        if(!data.containsKey(userAccount)){
            return false;
        }
        data.remove(userAccount);
        saveData(data);
        return true;
    }
}
