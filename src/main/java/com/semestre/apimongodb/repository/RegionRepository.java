package com.semestre.apimongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.semestre.apimongodb.model.Region;

public interface RegionRepository extends MongoRepository<Region, String> {
}