package com.example.quan_ly_kho.controller;

import com.example.quan_ly_kho.dto.ResultResponse;
import com.example.quan_ly_kho.service.BranchService;
import com.example.quan_ly_kho.utils.AppConstants;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/user/productbranch")
public class ProductBranchController {
    private BranchService branchService;
    @GetMapping ("/{branchId}")
    public ResponseEntity<ResultResponse> getProductsByBranches(
            @PathVariable("branchId") Long id,
            @RequestParam(value="pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
            @RequestParam(value="pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
            @RequestParam(value="sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,required = false) String sortDir
    ){
        ResultResponse rs = branchService.getProductByBranch(id,pageNo,pageSize,sortBy,sortDir);
        return ResponseEntity.ok(rs);
    }
}
