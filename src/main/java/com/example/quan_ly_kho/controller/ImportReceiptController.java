package com.example.quan_ly_kho.controller;

import com.example.quan_ly_kho.dto.ImportReceiptDetailDto;
import com.example.quan_ly_kho.dto.ImportReceiptDto;
import com.example.quan_ly_kho.dto.request.ImportReceiptDetailRequest;
import com.example.quan_ly_kho.dto.request.ImportReceiptRequest;
import com.example.quan_ly_kho.security.JwtTokenProvider;
import com.example.quan_ly_kho.service.ImportReceiptService;
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
}
