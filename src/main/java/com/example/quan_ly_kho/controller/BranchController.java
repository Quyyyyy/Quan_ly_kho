package com.example.quan_ly_kho.controller;

import com.example.quan_ly_kho.dto.BranchDto;
import com.example.quan_ly_kho.dto.ProductDto;
import com.example.quan_ly_kho.dto.ResultResponse;
import com.example.quan_ly_kho.service.BranchService;
import com.example.quan_ly_kho.utils.AppConstants;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/branch")
public class BranchController {
    private BranchService branchService;

    @GetMapping("/all")
    public ResponseEntity<List<BranchDto>> getBranches() {
        List<BranchDto> branchDtos = branchService.getBranches();
        return ResponseEntity.ok(branchDtos);
    }

    @GetMapping
    public ResponseEntity<ResultResponse> getAllBranches(
            @RequestParam(value="pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
            @RequestParam(value="pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
            @RequestParam(value="sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,required = false) String sortDir
    ){
        ResultResponse rs = branchService.getAllBranch(pageNo,pageSize,sortBy,sortDir);
        return ResponseEntity.ok(rs);
    }

    @PostMapping
    public ResponseEntity<BranchDto> createBranch(@RequestBody BranchDto branchDto){
        BranchDto branchDto1 = branchService.createBranch(branchDto);
        return ResponseEntity.ok(branchDto1);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchDto> getBranchById(@PathVariable("id") Long branchId){
        BranchDto branchDto = branchService.getBranchById(branchId);
        return ResponseEntity.ok(branchDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BranchDto> updateBranch(@PathVariable("id") Long branchId,
                                                  @RequestBody BranchDto branchDto){
        BranchDto branchDto1 = branchService.updateBranch(branchId,branchDto);
        return ResponseEntity.ok(branchDto1);
    }
}
