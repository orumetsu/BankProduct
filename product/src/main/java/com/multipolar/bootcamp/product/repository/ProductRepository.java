package com.multipolar.bootcamp.product.repository;

import com.multipolar.bootcamp.product.domain.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
