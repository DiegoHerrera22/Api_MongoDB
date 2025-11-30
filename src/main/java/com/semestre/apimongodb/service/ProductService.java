package com.semestre.apimongodb.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.semestre.apimongodb.model.Product;
import com.semestre.apimongodb.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    //Crud Create, Read, Update, Delete

    public Product addProduct(Product product) {
        product.setId(UUID.randomUUID().toString().split("-")[0]);
        return productRepository.save(product);
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Product findProductById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findAll().stream()
                .filter(product -> product.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    public Product updateProduct(Product product) {
        Product existingProduct = productRepository.findById(product.getId()).orElse(null);
        if (existingProduct != null) {
            existingProduct.setName(product.getName());
            existingProduct.setCategory(product.getCategory());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setStock(product.getStock());
            existingProduct.setCritical(product.getCritical());
            existingProduct.setImageUrl(product.getImageUrl());
            existingProduct.setDescription(product.getDescription());
            return productRepository.save(existingProduct);
        }
        return null;
    } 

    public String deleteProduct(String id) {
        productRepository.deleteById(id);
        return "Product with id " + id + " has been deleted.";
    }
}
