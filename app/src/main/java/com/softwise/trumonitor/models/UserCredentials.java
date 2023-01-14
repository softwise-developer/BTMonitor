package com.softwise.trumonitor.models;

public class UserCredentials {
    String email;
    String password;

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String str) {
        this.email = str;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String str) {
        this.password = str;
    }

    public UserCredentials(String str, String str2) {
        this.email = str;
        this.password = str2;
    }

    public UserCredentials() {
    }
}
