package com.semestre.apimongodb.dto;

import com.semestre.apimongodb.model.Region;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
    private String id;
    private String nombre;
    private String apellido;
    private String rut;
    private String direccion;
    private Region region;
    private String comuna;
    private String email;
    private String role;
}