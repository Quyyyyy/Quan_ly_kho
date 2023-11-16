package com.example.quan_ly_kho.service.Impl;

import com.example.quan_ly_kho.dto.ImportReceiptDetailDto;
import com.example.quan_ly_kho.dto.ImportReceiptDto;
import com.example.quan_ly_kho.dto.request.ImportReceiptDetailRequest;
import com.example.quan_ly_kho.dto.request.ImportReceiptRequest;
import com.example.quan_ly_kho.entity.*;
import com.example.quan_ly_kho.exception.ResourceNotFoundException;
import com.example.quan_ly_kho.repository.*;
import com.example.quan_ly_kho.service.ImportReceiptService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ImportReceiptServiceImpl implements ImportReceiptService {
    private ImportReceiptRepository importReceiptRepository;
    private ImportReceiptDetailRepository receiptDetailRepository;
    private ProductRepository productRepository;
    private UserRepository userRepository;
    private ProviderRepository providerRepository;
    private ProductBranchRepository productBranchRepository;
    private ModelMapper modelMapper;
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
        importReceipt.setImportDate(importReceiptRequest.getImportDate());

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


}
