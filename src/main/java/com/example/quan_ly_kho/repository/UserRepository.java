package com.example.quan_ly_kho.repository;

import com.example.quan_ly_kho.entity.Branch;
import com.example.quan_ly_kho.entity.User;
import com.example.quan_ly_kho.utils.AppConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    @Query(value="select nhan_vien.* from nhan_vien where nhan_vien.tai_khoan = :username" +
            " and nhan_vien.chi_nhanh_id = " + AppConstants.DEFAULT_BRANCH_ID,nativeQuery = true)
    Optional<User> findUserByUsername(String username);
    Boolean existsByUsername(String username);
    Page<User> findUserByBranch(Branch branch, Pageable pageable);
}
