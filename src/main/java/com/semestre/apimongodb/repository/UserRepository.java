package com.semestre.apimongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.semestre.apimongodb.model.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
}