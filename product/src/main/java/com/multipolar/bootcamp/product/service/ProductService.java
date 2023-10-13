package com.multipolar.bootcamp.product.service;


import com.multipolar.bootcamp.product.domain.Product;
import com.multipolar.bootcamp.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //get all products
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    //get product by id
    public Optional<Product> getProductById(String id){
        return productRepository.findById(id);
    }

    //create new product
    public Product createOrUpdateProduct(Product product){
        return productRepository.save(product);
    }

    // delete product
    public void deleteProductById(String id){
        productRepository.deleteById(id);
    }
}
