package com.example.quan_ly_kho.service.Impl;

import com.example.quan_ly_kho.dto.*;
import com.example.quan_ly_kho.dto.request.ExportReceiptDetailRequest;
import com.example.quan_ly_kho.dto.request.ExportReceiptRequest;
import com.example.quan_ly_kho.entity.*;
import com.example.quan_ly_kho.exception.APIException;
import com.example.quan_ly_kho.exception.ResourceNotFoundException;
import com.example.quan_ly_kho.repository.*;
import com.example.quan_ly_kho.service.ExportReceiptService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class ExportReceiptServiceImpl implements ExportReceiptService {
    private ExportReceiptRepository exportReceiptRepository;
    private ExportReceiptDetailRepository receiptDetailRepository;
    private ProductRepository productRepository;
    private UserRepository userRepository;
    private CustomerRepository customerRepository;
    private ProductBranchRepository productBranchRepository;
    private ModelMapper modelMapper;

    @Override
    public ResultResponse getAllExportReceipts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
        Page<ExportReceipt> exportReceipts = exportReceiptRepository.findAll(pageable);
        List<ExportReceipt> exportReceiptList = exportReceipts.getContent();
        List<ExportReceiptDto> contents = exportReceiptList.stream()
                .map(ex -> modelMapper.map(ex,ExportReceiptDto.class)).collect(Collectors.toList());

        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setContent(contents);
        resultResponse.setPageNo(exportReceipts.getNumber());
        resultResponse.setPageSize(exportReceipts.getSize());
        resultResponse.setTotalElements(exportReceipts.getTotalElements());
        resultResponse.setTotalPages(exportReceipts.getTotalPages());
        resultResponse.setLast(exportReceipts.isLast());
        return resultResponse;
    }

    @Override
    public ExportReceiptDto getExportReceiptById(Long id) {
        ExportReceipt exportReceipt = exportReceiptRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("ExportReceipt","id",id)
        );
        List<ExportReceiptDetail> exportReceiptDetails = receiptDetailRepository.findExportReceiptDetailByExportReceipt(exportReceipt);
        ExportReceiptDto exportReceiptDto = modelMapper.map(exportReceipt,ExportReceiptDto.class);
        List<ExportReceiptDetailDto> exportReceiptDetailDtos = exportReceiptDetails.stream()
                .map(ex->modelMapper.map(ex,ExportReceiptDetailDto.class)).collect(Collectors.toList());
        exportReceiptDto.setListExportReceiptDetails(exportReceiptDetailDtos);
        return exportReceiptDto;
    }

    @Override
    public ExportReceiptDto createExportReceipt(String username, ExportReceiptRequest exportReceiptRequest) {
        User user = userRepository.findUserByUsername(username).orElseThrow(
                ()->new UsernameNotFoundException("User not ")
        );

        Customer customer = customerRepository.findById(exportReceiptRequest.getCustomerId()).orElseThrow(
                ()->new ResourceNotFoundException("Customer","id", exportReceiptRequest.getCustomerId())
        );
        ExportReceipt exportReceipt = new ExportReceipt();
        exportReceipt.setUser(user);
        exportReceipt.setCustomer(customer);
        exportReceipt.setDescription(exportReceiptRequest.getDescription());
        exportReceipt.setExportDate(new Date());

        ExportReceipt exportReceipt1 = exportReceiptRepository.save(exportReceipt);
        return modelMapper.map(exportReceipt1, ExportReceiptDto.class);
    }

    @Override
    public ExportReceiptDetailDto createExportReceiptDetai(String username, Long exportReceiptId, ExportReceiptDetailRequest exportReceiptDetailRequest) {
        User user = userRepository.findUserByUsername(username).orElseThrow(
                ()->new UsernameNotFoundException("User not ")
        );
        Branch branch = user.getBranch();
        ExportReceipt exportReceipt = exportReceiptRepository.findById(exportReceiptId).orElseThrow(
                ()->new ResourceNotFoundException("ExportReceipt","id",exportReceiptId)
        );
        Product product = productRepository.findById(exportReceiptDetailRequest.getProductId()).orElseThrow(
                ()->new ResourceNotFoundException("Product","id",exportReceiptDetailRequest.getProductId())
        );
        ProductBranch productBranch = productBranchRepository.findByProductAndBranch(product,branch).orElseThrow(
                ()-> new APIException(HttpStatus.BAD_REQUEST,"Product not found Branch")
        );

        if(productBranch.getQuantity()<exportReceiptDetailRequest.getQuantity()){
            throw new APIException(HttpStatus.BAD_REQUEST,"Product not found Branch");
        } else{
            productBranch.setQuantity(productBranch.getQuantity() - exportReceiptDetailRequest.getQuantity());
        }
        productBranchRepository.save(productBranch);

        ExportReceiptDetail exportDetail = new ExportReceiptDetail();
        exportDetail.setExportReceipt(exportReceipt);
        exportDetail.setPrice(exportReceiptDetailRequest.getPrice());
        exportDetail.setQuantity(exportReceiptDetailRequest.getQuantity());
        exportDetail.setProduct(product);

        ExportReceiptDetail receiptDetail = receiptDetailRepository.save(exportDetail);
        return modelMapper.map(receiptDetail, ExportReceiptDetailDto.class);
    }

    @Override
    public ResultResponse getExportReceiptsByBranch(Long branchId, int pageNo, int pageSize, String sortDir) {
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<ExportReceipt> exportReceipts = exportReceiptRepository.findBranchId(branchId,sortDir,pageable);
        List<ExportReceipt> exportReceiptList = exportReceipts.getContent();
        List<ExportReceiptDto> contents = exportReceiptList.stream()
                .map(ex -> modelMapper.map(ex,ExportReceiptDto.class)).collect(Collectors.toList());

        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setContent(contents);
        resultResponse.setPageNo(exportReceipts.getNumber());
        resultResponse.setPageSize(exportReceipts.getSize());
        resultResponse.setTotalElements(exportReceipts.getTotalElements());
        resultResponse.setTotalPages(exportReceipts.getTotalPages());
        resultResponse.setLast(exportReceipts.isLast());
        return resultResponse;
    }
}
