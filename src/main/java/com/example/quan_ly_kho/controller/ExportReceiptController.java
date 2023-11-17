package com.example.quan_ly_kho.controller;

import com.example.quan_ly_kho.dto.ExportReceiptDetailDto;
import com.example.quan_ly_kho.dto.ExportReceiptDto;
import com.example.quan_ly_kho.dto.ResultResponse;
import com.example.quan_ly_kho.dto.request.ExportReceiptDetailRequest;
import com.example.quan_ly_kho.dto.request.ExportReceiptRequest;
import com.example.quan_ly_kho.security.JwtTokenProvider;
import com.example.quan_ly_kho.service.ExportReceiptService;
import com.example.quan_ly_kho.utils.AppConstants;
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

    @GetMapping
    public ResponseEntity<ResultResponse> getAllImportReceipts(
            @RequestParam(value="pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
            @RequestParam(value="pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
            @RequestParam(value="sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,required = false) String sortDir
    ){
        ResultResponse rs = exportReceiptService.getAllExportReceipts(pageNo,pageSize,sortBy,sortDir);
        return ResponseEntity.ok(rs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExportReceiptDto> getExportReceiptById(@PathVariable("id") Long id){
        ExportReceiptDto exportReceiptDto = exportReceiptService.getExportReceiptById(id);
        return ResponseEntity.ok(exportReceiptDto);
    }
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

    @GetMapping("/branch/{id}")
    public ResponseEntity<ResultResponse> getAllExportReceiptByBranches(
            @PathVariable("id") Long id,
            @RequestParam(value="pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
            @RequestParam(value="pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value="sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,required = false) String sortDir
    ){
        ResultResponse rs = exportReceiptService.getExportReceiptsByBranch(id,pageNo,pageSize,sortDir);
        return ResponseEntity.ok(rs);
    }
}
