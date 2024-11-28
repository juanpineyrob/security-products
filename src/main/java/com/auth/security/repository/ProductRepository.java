package com.auth.security.repository;

import com.auth.security.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @PreAuthorize("hasAuthority('SAVE_ONE_PRODUCT')")
    Product save(Product product);

}
