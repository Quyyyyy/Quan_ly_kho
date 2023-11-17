package com.example.quan_ly_kho.repository;

import com.example.quan_ly_kho.entity.Branch;
import com.example.quan_ly_kho.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findUserByUsername(String username);
    Boolean existsByUsername(String username);
    Page<User> findUserByBranch(Branch branch, Pageable pageable);
}
