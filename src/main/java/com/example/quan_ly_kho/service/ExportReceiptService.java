package com.example.quan_ly_kho.service;

import com.example.quan_ly_kho.dto.ExportReceiptDetailDto;
import com.example.quan_ly_kho.dto.ExportReceiptDto;
import com.example.quan_ly_kho.dto.ResultResponse;
import com.example.quan_ly_kho.dto.request.ExportReceiptDetailRequest;
import com.example.quan_ly_kho.dto.request.ExportReceiptRequest;

public interface ExportReceiptService {
    ResultResponse getAllExportReceipts(int pageNo, int pageSize, String sortBy, String sortDir);
    ExportReceiptDto getExportReceiptById(Long id);
    ExportReceiptDto createExportReceipt(String username, ExportReceiptRequest exportReceiptRequest);
    ExportReceiptDetailDto createExportReceiptDetai(String username, Long exportReceiptId, ExportReceiptDetailRequest exportReceiptDetailRequest);
    ResultResponse getExportReceiptsByBranch(Long branchId,int pageNo, int pageSize, String sortDir);
}
