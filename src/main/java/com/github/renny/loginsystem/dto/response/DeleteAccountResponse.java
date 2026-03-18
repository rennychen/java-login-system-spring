package com.github.renny.loginsystem.dto.response;

public class DeleteAccountResponse {
    private boolean success;
    private String message;

    public DeleteAccountResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
