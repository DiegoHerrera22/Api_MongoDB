package com._Semestre.Api_MongoDb.repository;
import com._Semestre.Api_MongoDb.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String>{

}
