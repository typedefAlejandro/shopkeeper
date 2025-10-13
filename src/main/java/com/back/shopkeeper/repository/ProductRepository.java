package com.back.shopkeeper.repository;

import com.back.shopkeeper.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
