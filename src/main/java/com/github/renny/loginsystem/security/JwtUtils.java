package com.github.renny.loginsystem.security;


import com.github.renny.loginsystem.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    private final Key key;
    private final long expiration;

    public JwtUtils(JwtProperties properties){
        this.key = Keys.hmacShaKeyFor(properties.getSecret().getBytes());
        this.expiration = properties.getExpiration();
    }

    public String createToken(String account){
        return Jwts.builder()
                .setSubject(account)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    public Claims parseToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getAccountFromToken(String token){
        Claims claims = parseToken(token);
        return claims.getSubject();
    }
    
    public long getExpirationFromToken(String token){
        Claims claims = parseToken(token);
        return claims.getExpiration().getTime();
    }
}
