package com.example.quan_ly_kho.repository;

import com.example.quan_ly_kho.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
