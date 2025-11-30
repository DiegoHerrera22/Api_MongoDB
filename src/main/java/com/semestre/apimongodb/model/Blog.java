package com.semestre.apimongodb.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "blogs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blog {
    @Id
    private String id;
    private String title;
    private String content;
    private String authorId;
    private Instant publishedAt;
}