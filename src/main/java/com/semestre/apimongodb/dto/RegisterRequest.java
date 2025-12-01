package com.semestre.apimongodb.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private String regionId;
    private String role;
}