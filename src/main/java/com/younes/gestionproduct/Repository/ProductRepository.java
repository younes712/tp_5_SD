package com.younes.gestionproduct.Repository;

import com.younes.gestionproduct.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Product> findByIdOrNameContainingIgnoreCase(Long id, String name, Pageable pageable);
}