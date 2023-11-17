package com.example.quan_ly_kho.repository;

import com.example.quan_ly_kho.entity.ExportReceipt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExportReceiptRepository extends JpaRepository<ExportReceipt,Long> {
    @Query(value = "SELECT phieu_xuat.* FROM phieu_xuat " +
            "INNER JOIN nhan_vien ON phieu_xuat.nhan_vien_id = nhan_vien.id " +
            "WHERE nhan_vien.chi_nhanh_id=:id " +
            "ORDER BY CASE WHEN :sortDir = 'ASC' THEN phieu_xuat.id END ASC, " +
            "CASE WHEN :sortDir = 'DESC' THEN phieu_xuat.id END DESC",nativeQuery = true)

//     @Query(value = "SELECT phieu_xuat.*,nhan_vien.chi_nhanh_id FROM phieu_xuat " +
//        "INNER JOIN nhan_vien ON phieu_xuat.nhan_vien_id = nhan_vien.id " +
//        "WHERE nhan_vien.chi_nhanh_id = :id " +
//        "ORDER BY CASE WHEN :sortDir = 'ASC' THEN phieu_xuat.id END ASC, " +
//        "CASE WHEN :sortDir = 'DESC' THEN phieu_xuat.id END DESC", nativeQuery = true)
    Page<ExportReceipt> findBranchId(@Param("id") Long id,
                                     @Param("sortDir") String sortDir, Pageable pageable);
}
