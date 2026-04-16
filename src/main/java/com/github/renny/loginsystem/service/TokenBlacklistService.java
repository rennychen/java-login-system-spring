package com.github.renny.loginsystem.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TokenBlacklistService {
    private final RedisTemplate<String,String> redisTemplate;
    private static final String BLACKLIST_PREFIX = "blacklist";

    public TokenBlacklistService(RedisTemplate<String,String> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    public void addToBlacklist(String token,long ttl){
        if(ttl > 0){
            redisTemplate.opsForValue().set(
                    BLACKLIST_PREFIX + token,
                    "true",
                    ttl,
                    TimeUnit.MILLISECONDS
            );
        }
    }

    public boolean isBlacklisted(String token){
        return Boolean.TRUE.equals(redisTemplate.hasKey((BLACKLIST_PREFIX + token)));
    }

}
