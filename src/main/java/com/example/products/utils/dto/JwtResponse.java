package com.example.products.utils.dto;


public class JwtResponse {
    private String token;
    private String type = "Bearer";

    // Constructor that sets the token
    public JwtResponse(String token) {
        this.token = token;
    }

    // Getter for token
    public String getToken() {
        return token;
    }

    // Getter for type
    public String getType() {
        return type;
    }
}

