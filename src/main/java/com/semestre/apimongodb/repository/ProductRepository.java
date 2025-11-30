package com.semestre.apimongodb.repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.semestre.apimongodb.model.Product;

public interface ProductRepository extends MongoRepository<Product, String>{

}
