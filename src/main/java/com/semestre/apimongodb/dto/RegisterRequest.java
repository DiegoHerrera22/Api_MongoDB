package com.semestre.apimongodb.dto;

import com.semestre.apimongodb.model.Region;
import lombok.Data;

@Data
public class RegisterRequest {
    private String nombre;
    private String apellido;
    private String rut;
    private String direccion;
    private Region region;
    private String comuna;
    private String email;
    private String password;
}