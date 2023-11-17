package com.example.quan_ly_kho.service.Impl;

import com.example.quan_ly_kho.dto.BranchDto;
import com.example.quan_ly_kho.dto.ProductBranchDto;
import com.example.quan_ly_kho.dto.ResultResponse;
import com.example.quan_ly_kho.entity.Branch;
import com.example.quan_ly_kho.entity.ProductBranch;
import com.example.quan_ly_kho.exception.ResourceNotFoundException;
import com.example.quan_ly_kho.repository.BranchRepository;
import com.example.quan_ly_kho.repository.ProductBranchRepository;
import com.example.quan_ly_kho.service.BranchService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BranchServiceImpl implements BranchService {
    private BranchRepository branchRepository;
    private ProductBranchRepository productBranchRepository;
    private ModelMapper modelMapper;

    @Override
    public List<BranchDto> getBranches() {
        List<Branch> branches = branchRepository.findAll();
        List<BranchDto> branchDtos = branches.stream().map(branch -> modelMapper.map(branch, BranchDto.class))
                .collect(Collectors.toList());
        return branchDtos;
    }

    @Override
    public ResultResponse getAllBranch(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
        Page<Branch> branches = branchRepository.findAll(pageable);
        List<Branch> listOfBranches = branches.getContent();
        List<BranchDto> contents = listOfBranches.stream()
                .map(branch -> modelMapper.map(branch,BranchDto.class)).collect(Collectors.toList());

        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setContent(contents);
        resultResponse.setPageNo(branches.getNumber());
        resultResponse.setPageSize(branches.getSize());
        resultResponse.setTotalElements(branches.getTotalElements());
        resultResponse.setTotalPages(branches.getTotalPages());
        resultResponse.setLast(branches.isLast());
        return resultResponse;
    }

    @Override
    public BranchDto createBranch(BranchDto branchDto) {
        Branch branch = modelMapper.map(branchDto,Branch.class);
        Branch saveBran = branchRepository.save(branch);
        BranchDto branchDto1 = modelMapper.map(saveBran,BranchDto.class);
        return branchDto1;
    }

    @Override
    public BranchDto getBranchById(Long branchId) {
        Branch branch = branchRepository.findById(branchId).orElseThrow(
                ()->new ResourceNotFoundException("Branch","id",branchId)
        );
        BranchDto branchDto = modelMapper.map(branch,BranchDto.class);
        return branchDto;
    }

    @Override
    public BranchDto updateBranch(Long branchId,BranchDto branchDto) {
        Branch branch = branchRepository.findById(branchId).orElseThrow(
                ()->new ResourceNotFoundException("Branch","id",branchId)
        );
        branch.setName(branchDto.getName());
        branch.setEmail(branchDto.getEmail());
        branch.setAddress(branch.getAddress());
        branch.setPhone(branch.getPhone());
        Branch saveBranch = branchRepository.save(branch);
        return modelMapper.map(saveBranch,BranchDto.class);
    }

    @Override
    public ResultResponse getProductByBranch(Long branchId, int pageNo, int pageSize, String sortBy, String sortDir) {
        Branch branch = branchRepository.findById(branchId).orElseThrow(
                ()->new ResourceNotFoundException("Branch","id",branchId)
        );
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
        Page<ProductBranch> productBranches = productBranchRepository.findByBranch(branch,pageable);
        List<ProductBranch> productBranchList = productBranches.getContent();

        List<ProductBranchDto> contents = productBranchList.stream()
                .map(pb -> modelMapper.map(pb,ProductBranchDto.class)).collect(Collectors.toList());

        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setContent(contents);
        resultResponse.setPageNo(productBranches.getNumber());
        resultResponse.setPageSize(productBranches.getSize());
        resultResponse.setTotalElements(productBranches.getTotalElements());
        resultResponse.setTotalPages(productBranches.getTotalPages());
        resultResponse.setLast(productBranches.isLast());
        return resultResponse;
    }
}
