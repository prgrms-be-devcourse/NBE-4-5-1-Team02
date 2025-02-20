package com.team2.demo.domain.product.repository;

import com.team2.demo.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByCategoryLike(String category, Pageable pageable);
}
