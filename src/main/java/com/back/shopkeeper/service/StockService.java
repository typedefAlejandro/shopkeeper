package com.back.shopkeeper.service;

import com.back.shopkeeper.exception.ResourceNotFoundException;
import com.back.shopkeeper.model.entity.Product;
import com.back.shopkeeper.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    private final ProductRepository productRepository;

    public StockService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product create(Product product) {
        return productRepository.save(product);
    }

    public Optional<Product> update(Long id, Product details) {
        return productRepository.findById(id).map(product -> {
            product.setName(details.getName());
            product.setCost(details.getCost());
            product.setPrice(details.getPrice());
            product.setQuantity(details.getQuantity());
            return productRepository.save(product);
        });
    }

    public boolean delete(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
