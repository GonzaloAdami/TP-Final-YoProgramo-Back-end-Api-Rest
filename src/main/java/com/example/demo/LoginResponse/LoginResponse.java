/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.LoginResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author gonza
 */
public class LoginResponse {
    @JsonProperty("token")
    private String token;
    
    @JsonProperty("id")
    private Long id;
    
    public LoginResponse(String token, Long id) {
        this.token = token;
        this.id = id;
    }
    
    // Getters y setters
}
