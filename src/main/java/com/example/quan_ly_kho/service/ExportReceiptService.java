package com.example.quan_ly_kho.service;

import com.example.quan_ly_kho.dto.ExportReceiptDetailDto;
import com.example.quan_ly_kho.dto.ExportReceiptDto;
import com.example.quan_ly_kho.dto.request.ExportReceiptDetailRequest;
import com.example.quan_ly_kho.dto.request.ExportReceiptRequest;

public interface ExportReceiptService {
    ExportReceiptDto createExportReceipt(String username, ExportReceiptRequest exportReceiptRequest);
    ExportReceiptDetailDto createExportReceiptDetai(String username, Long exportReceiptId, ExportReceiptDetailRequest exportReceiptDetailRequest);
}
