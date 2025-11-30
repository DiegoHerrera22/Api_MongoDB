package com.semestre.apimongodb.model;

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
    private String name;
    private String country;
    private String abbreviation;
}