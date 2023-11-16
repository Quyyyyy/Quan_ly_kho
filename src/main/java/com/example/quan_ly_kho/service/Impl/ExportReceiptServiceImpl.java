package com.example.quan_ly_kho.service.Impl;

import com.example.quan_ly_kho.dto.ExportReceiptDetailDto;
import com.example.quan_ly_kho.dto.ExportReceiptDto;
import com.example.quan_ly_kho.dto.request.ExportReceiptDetailRequest;
import com.example.quan_ly_kho.dto.request.ExportReceiptRequest;
import com.example.quan_ly_kho.entity.*;
import com.example.quan_ly_kho.exception.APIException;
import com.example.quan_ly_kho.exception.ResourceNotFoundException;
import com.example.quan_ly_kho.repository.*;
import com.example.quan_ly_kho.service.ExportReceiptService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ExportReceiptServiceImpl implements ExportReceiptService {
    private ExportReceiptRepository exportReceiptRepository;
    private ExportReceiptDetailRepository receiptDetailRepository;
    private ProductRepository productRepository;
    private UserRepository userRepository;
    private CustomerRepository customerRepository;
    private ProductBranchRepository productBranchRepository;
    private ModelMapper modelMapper;

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
        exportReceipt.setExportDate(exportReceiptRequest.getImportDate());

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
}
