package com.example.quan_ly_kho.service;

import com.example.quan_ly_kho.dto.ImportReceiptDetailDto;
import com.example.quan_ly_kho.dto.ImportReceiptDto;
import com.example.quan_ly_kho.dto.request.ImportReceiptDetailRequest;
import com.example.quan_ly_kho.dto.request.ImportReceiptRequest;

public interface ImportReceiptService {
    ImportReceiptDto createImportReceipt(String username,ImportReceiptRequest importReceiptRequest);
    ImportReceiptDetailDto createImportReceiptDetai(String username,Long importReceiptId, ImportReceiptDetailRequest importReceiptDetailRequest);
}
