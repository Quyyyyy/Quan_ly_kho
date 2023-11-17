package com.example.quan_ly_kho.service;

import com.example.quan_ly_kho.dto.ExportReceiptDto;
import com.example.quan_ly_kho.dto.ImportReceiptDetailDto;
import com.example.quan_ly_kho.dto.ImportReceiptDto;
import com.example.quan_ly_kho.dto.ResultResponse;
import com.example.quan_ly_kho.dto.request.ImportReceiptDetailRequest;
import com.example.quan_ly_kho.dto.request.ImportReceiptRequest;

public interface ImportReceiptService {
    ResultResponse getAllImportReceipts(int pageNo, int pageSize, String sortBy, String sortDir);
    ImportReceiptDto getImportReceiptById(Long id);
    ImportReceiptDto createImportReceipt(String username,ImportReceiptRequest importReceiptRequest);
    ImportReceiptDetailDto createImportReceiptDetai(String username,Long importReceiptId, ImportReceiptDetailRequest importReceiptDetailRequest);
    ResultResponse getImportReceiptsByBranch(Long branchId,int pageNo, int pageSize, String sortDir);
}
