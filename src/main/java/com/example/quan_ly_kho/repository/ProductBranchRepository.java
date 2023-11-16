package com.example.quan_ly_kho.repository;

import com.example.quan_ly_kho.entity.Branch;
import com.example.quan_ly_kho.entity.Product;
import com.example.quan_ly_kho.entity.ProductBranch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductBranchRepository extends JpaRepository<ProductBranch,Long> {
    Optional<ProductBranch> findByProductAndBranch(Product product, Branch branch);
}
