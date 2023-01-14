package com.softwise.trumonitor.models;

public class RefreshTokenResponse {
    private String message;
    private boolean success;

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean z) {
        this.success = z;
    }
}
