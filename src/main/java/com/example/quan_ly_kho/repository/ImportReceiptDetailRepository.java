package com.example.quan_ly_kho.repository;

import com.example.quan_ly_kho.entity.ImportReceipt;
import com.example.quan_ly_kho.entity.ImportReceiptDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImportReceiptDetailRepository extends JpaRepository<ImportReceiptDetail,Long> {
    List<ImportReceiptDetail> findImportReceiptDetailByImportReceipt(ImportReceipt importReceipt);
}
