package com.back.shopkeeper.service;

import com.back.shopkeeper.exception.ResourceNotFoundException;
import com.back.shopkeeper.model.entity.Product;
import com.back.shopkeeper.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

    private final ProductRepository productRepository;

    public StockService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));
    }

    public Product create(Product product) {
        return productRepository.save(product);
    }

    public Product update(Long id, Product details) {
        Product product = findById(id);

        product.setName(details.getName());
        product.setCost(details.getCost());
        product.setPrice(details.getPrice());
        product.setQuantity(details.getQuantity());

        return productRepository.save(product);
    }

    public void delete(Long id) {
        Product product = findById(id);
        productRepository.delete(product);
    }
}
