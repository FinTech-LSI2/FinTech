package com.example.identotyservice.dto;

public class AuthRequest {
    private String username;
    private String password;

    // Getter for username
    public String getUsername() {
        return username;
    }

    // Setter for username
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter for password
    public String getPassword() {
        return password;
    }

    // Setter for password
    public void setPassword(String password) {
        this.password = password;
    }


    // Default constructor
    public AuthRequest() {}

    // Constructor with parameters
    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
