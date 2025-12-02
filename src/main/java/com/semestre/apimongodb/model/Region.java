package com.semestre.apimongodb.model;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "regions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Region {

    @Id
    private String id;

    private String nombre;       // Nombre de la región
    private List<String> comunas; // Lista de comunas
}
