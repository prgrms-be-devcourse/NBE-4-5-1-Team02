package com.team2.demo.domain.product.repository;

import com.team2.demo.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {
    Page<Product> findAllByCategoryLike(String category, Pageable pageable);

    Page<Product> findAllByProductNameLike(String productName, Pageable pageable);

    Optional<Product> findByProductUuid(String productUuid);

    @NativeQuery(value = """
            SELECT PRODUCT.* FROM PRODUCT
            LEFT JOIN PRODUCT_ORDER_RELATION
            ON PRODUCT.PRODUCT_UUID=PRODUCT_ORDER_RELATION.PRODUCT_UUID
            WHERE PRODUCT_ORDER_RELATION.ORDER_UUID= :orderId;
            """,
            countQuery = """
            SELECT COUNT(*) FROM PRODUCT
            LEFT JOIN PRODUCT_ORDER_RELATION
            ON PRODUCT.PRODUCT_UUID=PRODUCT_ORDER_RELATION.PRODUCT_UUID
            WHERE PRODUCT_ORDER_RELATION.ORDER_UUID= :orderId;
            """)
    Page<Product> findAllByOrderId(@Param(value = "orderId") String orderId, Pageable pageable);
}
