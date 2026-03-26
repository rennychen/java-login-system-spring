package com.github.renny.loginsystem.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String secret;
    private long ecpiration;
    private long refreshExpiration;
    private String header;
    private String prefix;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getEcpiration() {
        return ecpiration;
    }

    public void setEcpiration(long ecpiration) {
        this.ecpiration = ecpiration;
    }

    public long getRefreshExpiration() {
        return refreshExpiration;
    }

    public void setRefreshExpiration(long refreshExpiration) {
        this.refreshExpiration = refreshExpiration;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
