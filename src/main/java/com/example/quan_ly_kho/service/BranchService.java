package com.example.quan_ly_kho.service;

import com.example.quan_ly_kho.dto.BranchDto;
import com.example.quan_ly_kho.dto.ResultResponse;

import java.util.List;

public interface BranchService {
    List<BranchDto> getBranches();
    ResultResponse getAllBranch(int pageNo, int pageSize, String sortBy, String sortDir);
    BranchDto createBranch(BranchDto branchDto);
    BranchDto getBranchById(Long branchId);
    BranchDto updateBranch(Long branchId,BranchDto branchDto);
    ResultResponse getProductByBranch(Long branchId,int pageNo, int pageSize, String sortBy, String sortDir);
}
