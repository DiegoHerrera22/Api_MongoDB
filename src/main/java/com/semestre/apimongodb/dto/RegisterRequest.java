package com.semestre.apimongodb.dto;

import com.semestre.apimongodb.model.Region;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private Region region;
}