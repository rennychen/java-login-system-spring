package com.github.renny.loginsystem.policy;

import com.github.renny.loginsystem.expection.InvalidAccountException;

public class DefaultAccountPolicy implements AccountPolicy{
    @Override
    public void validate(String account) {
        if (account == null || account.isBlank()) {
            throw new InvalidAccountException("帳號不可為空");
        }

        if (account.length() < 6 || account.length() > 20) {
            throw new InvalidAccountException("帳號長度需為 6~20 字");
        }

        if (!account.matches("^[a-zA-Z].*")) {
            throw new InvalidAccountException("帳號必須以英文字母開頭");
        }

        if (!account.matches("^[a-zA-Z0-9]+$")) {
            throw new InvalidAccountException("帳號只能包含英文字母與數字");
        }
    }
}
