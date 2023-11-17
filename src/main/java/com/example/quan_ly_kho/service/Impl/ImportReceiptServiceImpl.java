package com.example.quan_ly_kho.service.Impl;

import com.example.quan_ly_kho.dto.*;
import com.example.quan_ly_kho.dto.request.ImportReceiptDetailRequest;
import com.example.quan_ly_kho.dto.request.ImportReceiptRequest;
import com.example.quan_ly_kho.entity.*;
import com.example.quan_ly_kho.exception.ResourceNotFoundException;
import com.example.quan_ly_kho.repository.*;
import com.example.quan_ly_kho.service.ImportReceiptService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class ImportReceiptServiceImpl implements ImportReceiptService {
    private ImportReceiptRepository importReceiptRepository;
    private ImportReceiptDetailRepository receiptDetailRepository;
    private ProductRepository productRepository;
    private UserRepository userRepository;
    private ProviderRepository providerRepository;
    private ProductBranchRepository productBranchRepository;
    private ModelMapper modelMapper;

    @Override
    public ResultResponse getAllImportReceipts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
        Page<ImportReceipt> importReceipts = importReceiptRepository.findAll(pageable);
        List<ImportReceipt> importReceiptList = importReceipts.getContent();
        List<ImportReceiptDto> contents = importReceiptList.stream()
                .map(ex -> modelMapper.map(ex,ImportReceiptDto.class)).collect(Collectors.toList());

        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setContent(contents);
        resultResponse.setPageNo(importReceipts.getNumber());
        resultResponse.setPageSize(importReceipts.getSize());
        resultResponse.setTotalElements(importReceipts.getTotalElements());
        resultResponse.setTotalPages(importReceipts.getTotalPages());
        resultResponse.setLast(importReceipts.isLast());
        return resultResponse;
    }

    @Override
    public ImportReceiptDto getImportReceiptById(Long id) {
        ImportReceipt importReceipt = importReceiptRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("ImportReceipt","id",id)
        );
        List<ImportReceiptDetail> importReceiptDetails = receiptDetailRepository.findImportReceiptDetailByImportReceipt(importReceipt);
        ImportReceiptDto importReceiptDto = modelMapper.map(importReceipt,ImportReceiptDto.class);
        List<ImportReceiptDetailDto> importReceiptDetailDtos = importReceiptDetails.stream()
                .map(im->modelMapper.map(im,ImportReceiptDetailDto.class)).collect(Collectors.toList());
        importReceiptDto.setListImportReceiptDetails(importReceiptDetailDtos);
        return importReceiptDto;
    }

    @Override
    public ImportReceiptDto createImportReceipt(String username, ImportReceiptRequest importReceiptRequest) {
        User user = userRepository.findUserByUsername(username).orElseThrow(
                ()->new UsernameNotFoundException("User not ")
        );

        Provider provider = providerRepository.findById(importReceiptRequest.getProviderId()).orElseThrow(
                ()->new ResourceNotFoundException("Provider","id", importReceiptRequest.getProviderId())
        );
        ImportReceipt importReceipt = new ImportReceipt();
        importReceipt.setUser(user);
        importReceipt.setProvider(provider);
        importReceipt.setDescription(importReceiptRequest.getDescription());

//        SimpleDateFormat df = new SimpleDateFormat("mm:HH:ss dd/MM/yyyy");

        importReceipt.setImportDate(new Date());

        ImportReceipt importReceipt1 = importReceiptRepository.save(importReceipt);
        return modelMapper.map(importReceipt1,ImportReceiptDto.class);
    }

    @Override
    public ImportReceiptDetailDto createImportReceiptDetai(String username,
                                                           Long importReceiptId,
                                                           ImportReceiptDetailRequest importReceiptDetailRequest) {
        User user = userRepository.findUserByUsername(username).orElseThrow(
                ()->new UsernameNotFoundException("User not ")
        );
        Branch branch = user.getBranch();
        ImportReceipt importReceipt = importReceiptRepository.findById(importReceiptId).orElseThrow(
                ()->new ResourceNotFoundException("ImportReceipt","id",importReceiptId)
        );
        Product product = productRepository.findById(importReceiptDetailRequest.getProductId()).orElseThrow(
                ()->new ResourceNotFoundException("Product","id",importReceiptDetailRequest.getProductId())
        );
        ProductBranch productBranch = productBranchRepository.findByProductAndBranch(product,branch).orElse(null);

        if(productBranch == null){
            productBranch = new ProductBranch();
            productBranch.setBranch(branch);
            productBranch.setProduct(product);
            productBranch.setQuantity(importReceiptDetailRequest.getQuantity());
        } else{
            productBranch.setQuantity(productBranch.getQuantity() + importReceiptDetailRequest.getQuantity());
        }
        productBranchRepository.save(productBranch);

        ImportReceiptDetail importDetail = new ImportReceiptDetail();
        importDetail.setImportReceipt(importReceipt);
        importDetail.setPrice(importReceiptDetailRequest.getPrice());
        importDetail.setQuantity(importReceiptDetailRequest.getQuantity());
        importDetail.setProduct(product);

        ImportReceiptDetail receiptDetail = receiptDetailRepository.save(importDetail);
        return modelMapper.map(receiptDetail, ImportReceiptDetailDto.class);
    }

    @Override
    public ResultResponse getImportReceiptsByBranch(Long branchId, int pageNo, int pageSize, String sortDir) {
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<ImportReceipt> importReceipts = importReceiptRepository.findByBranchId(branchId,sortDir,pageable);
        List<ImportReceipt> importReceiptList = importReceipts.getContent();
        List<ImportReceiptDto> contents = importReceiptList.stream()
                .map(ex -> modelMapper.map(ex,ImportReceiptDto.class)).collect(Collectors.toList());

        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setContent(contents);
        resultResponse.setPageNo(importReceipts.getNumber());
        resultResponse.setPageSize(importReceipts.getSize());
        resultResponse.setTotalElements(importReceipts.getTotalElements());
        resultResponse.setTotalPages(importReceipts.getTotalPages());
        resultResponse.setLast(importReceipts.isLast());
        return resultResponse;
    }
}
