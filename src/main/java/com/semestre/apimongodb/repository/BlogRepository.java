package com.semestre.apimongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.semestre.apimongodb.model.Blog;

public interface BlogRepository extends MongoRepository<Blog, String> {
}