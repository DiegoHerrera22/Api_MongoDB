package com.semestre.apimongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.semestre.apimongodb.model.User;

public interface UserRepository extends MongoRepository<User, String> {
}