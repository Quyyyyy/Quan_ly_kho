package com.example.quan_ly_kho.repository;

import com.example.quan_ly_kho.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
