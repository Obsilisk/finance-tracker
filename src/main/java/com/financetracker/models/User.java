package com.financetracker.models;

public class User {
    private int id;
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and setters omitted for brevity
    public String getUsername() { return username; }
    public String getPassword() { return password; }
}
