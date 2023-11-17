package com.example.quan_ly_kho.controller;

import com.example.quan_ly_kho.dto.ImportReceiptDetailDto;
import com.example.quan_ly_kho.dto.ImportReceiptDto;
import com.example.quan_ly_kho.dto.ResultResponse;
import com.example.quan_ly_kho.dto.request.ImportReceiptDetailRequest;
import com.example.quan_ly_kho.dto.request.ImportReceiptRequest;
import com.example.quan_ly_kho.security.JwtTokenProvider;
import com.example.quan_ly_kho.service.ImportReceiptService;
import com.example.quan_ly_kho.utils.AppConstants;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/user/import")
public class ImportReceiptController {
    private ImportReceiptService importReceiptService;
    private JwtTokenProvider jwtTokenProvider;

    @GetMapping
    public ResponseEntity<ResultResponse> getAllImportReceipts(
            @RequestParam(value="pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
            @RequestParam(value="pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
            @RequestParam(value="sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,required = false) String sortDir
    ){
        ResultResponse rs = importReceiptService.getAllImportReceipts(pageNo,pageSize,sortBy,sortDir);
        return ResponseEntity.ok(rs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImportReceiptDto> getImportReceiptById(@PathVariable("id") Long id){
        ImportReceiptDto importReceiptDto = importReceiptService.getImportReceiptById(id);
        return ResponseEntity.ok(importReceiptDto);
    }

    @PostMapping
    public ResponseEntity<ImportReceiptDto> createImportReceipt(
            HttpServletRequest httpServletRequest,
            @RequestBody ImportReceiptRequest importReceiptRequest){
        String token = httpServletRequest.getHeader("Authorization").substring(7);
        String username = jwtTokenProvider.extractUsername(token);
        ImportReceiptDto importReceiptDto = importReceiptService.createImportReceipt(username,importReceiptRequest);
        return ResponseEntity.ok(importReceiptDto);
    }

    @PostMapping("/{id}")
    public ResponseEntity<ImportReceiptDetailDto> createImportReceiptDetai(
            HttpServletRequest httpServletRequest,
            @PathVariable("id") Long importReceiptId,
            @RequestBody ImportReceiptDetailRequest importReceiptRequest
    ){
        String token = httpServletRequest.getHeader("Authorization").substring(7);
        String username = jwtTokenProvider.extractUsername(token);
        ImportReceiptDetailDto importReceiptDetailDto =
                importReceiptService.createImportReceiptDetai(username, importReceiptId,importReceiptRequest);
        return ResponseEntity.ok(importReceiptDetailDto);
    }

    @GetMapping("/branch/{id}")
    public ResponseEntity<ResultResponse> getAllImportReceiptByBranches(
            @PathVariable("id") Long id,
            @RequestParam(value="pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
            @RequestParam(value="pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value="sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,required = false) String sortDir
    ){
        ResultResponse rs = importReceiptService.getImportReceiptsByBranch(id,pageNo,pageSize,sortDir);
        return ResponseEntity.ok(rs);
    }
}
