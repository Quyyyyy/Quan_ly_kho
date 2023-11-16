package com.example.quan_ly_kho.repository;

import com.example.quan_ly_kho.entity.ExportReceipt;
import com.example.quan_ly_kho.entity.ExportReceiptDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExportReceiptDetailRepository extends JpaRepository<ExportReceiptDetail,Long> {
    List<ExportReceiptDetail> findExportReceiptDetailByExportReceipt(ExportReceipt exportReceipt);
}
