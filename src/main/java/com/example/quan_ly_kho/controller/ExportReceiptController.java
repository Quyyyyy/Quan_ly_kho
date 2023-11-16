package com.example.quan_ly_kho.controller;

import com.example.quan_ly_kho.dto.ExportReceiptDetailDto;
import com.example.quan_ly_kho.dto.ExportReceiptDto;
import com.example.quan_ly_kho.dto.request.ExportReceiptDetailRequest;
import com.example.quan_ly_kho.dto.request.ExportReceiptRequest;
import com.example.quan_ly_kho.security.JwtTokenProvider;
import com.example.quan_ly_kho.service.ExportReceiptService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/user/export")
public class ExportReceiptController {
    private ExportReceiptService exportReceiptService;
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public ResponseEntity<ExportReceiptDto> createExportReceipt(
            HttpServletRequest httpServletRequest,
            @RequestBody ExportReceiptRequest exportReceiptRequest){
        String token = httpServletRequest.getHeader("Authorization").substring(7);
        String username = jwtTokenProvider.extractUsername(token);
        ExportReceiptDto exportReceiptDto = exportReceiptService.createExportReceipt(username,exportReceiptRequest);
        return ResponseEntity.ok(exportReceiptDto);
    }

    @PostMapping("/{id}")
    public ResponseEntity<ExportReceiptDetailDto> createExportReceiptDetai(
            HttpServletRequest httpServletRequest,
            @PathVariable("id") Long importReceiptId,
            @RequestBody ExportReceiptDetailRequest exportReceiptRequest
    ){
        String token = httpServletRequest.getHeader("Authorization").substring(7);
        String username = jwtTokenProvider.extractUsername(token);
        ExportReceiptDetailDto exportReceiptDetailDto =
                exportReceiptService.createExportReceiptDetai(username, importReceiptId,exportReceiptRequest);
        return ResponseEntity.ok(exportReceiptDetailDto);
    }
}
