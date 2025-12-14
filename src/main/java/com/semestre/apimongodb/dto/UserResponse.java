package com.semestre.apimongodb.dto;

import com.semestre.apimongodb.model.Region;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
    private String id;
    private String username;
    private String email;
    private Region region;
    private String role;
}
