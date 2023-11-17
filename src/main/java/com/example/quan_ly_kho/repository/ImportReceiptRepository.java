package com.example.quan_ly_kho.repository;

import com.example.quan_ly_kho.entity.ImportReceipt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ImportReceiptRepository extends JpaRepository<ImportReceipt,Long> {

//    @Query(value = "select phieu_nhap.* from phieu_nhap " +
//            "inner join nhan_vien on phieu_nhap.nhan_vien_id = nhan_vien.id " +
//            "where nhan_vien.chi_nhanh_id = :id order by phieu_nhap.id :sortDir",nativeQuery = true)
    @Query(value = "SELECT phieu_nhap.* FROM phieu_nhap " +
        "INNER JOIN nhan_vien ON phieu_nhap.nhan_vien_id = nhan_vien.id " +
        "WHERE nhan_vien.chi_nhanh_id = :id " +
        "ORDER BY CASE WHEN :sortDir = 'ASC' THEN phieu_nhap.id END ASC, " +
        "CASE WHEN :sortDir = 'DESC' THEN phieu_nhap.id END DESC", nativeQuery = true)
    Page<ImportReceipt> findByBranchId(@Param("id") Long id,
                                       @Param("sortDir") String sortDir, Pageable pageable);
}
