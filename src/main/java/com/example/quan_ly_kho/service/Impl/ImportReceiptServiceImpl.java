package com.example.quan_ly_kho.service.Impl;


import com.example.quan_ly_kho.dto.ImportReceiptDetailDto;
import com.example.quan_ly_kho.dto.ImportReceiptDto;
import com.example.quan_ly_kho.dto.request.ImportReceiptDetailRequest;
import com.example.quan_ly_kho.dto.request.ImportReceiptRequest;
import com.example.quan_ly_kho.entity.ImportReceipt;
import com.example.quan_ly_kho.entity.Provider;
import com.example.quan_ly_kho.entity.User;
import com.example.quan_ly_kho.exception.ResourceNotFoundException;
import com.example.quan_ly_kho.repository.ImportReceiptRepository;
import com.example.quan_ly_kho.repository.ProviderRepository;
import com.example.quan_ly_kho.repository.UserRepository;
import com.example.quan_ly_kho.service.ImportReceiptService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Transactional
public class ImportReceiptServiceImpl implements ImportReceiptService {
    private ImportReceiptRepository importReceiptRepository;
    private ModelMapper modelMapper;
    private UserRepository userRepository;
    private ProviderRepository providerRepository;

    @Override
    public ImportReceiptDto createImportReceipt(String username, ImportReceiptRequest importReceiptRequest) {
        User user = userRepository.findUserByUsername(username).get();
        Provider provider = providerRepository.findById(importReceiptRequest.getProviderId()).orElseThrow(
                ()-> new ResourceNotFoundException("Provider","id", importReceiptRequest.getProviderId())
        );
        ImportReceipt importReceipt = new ImportReceipt();
        importReceipt.setUser(user);
        importReceipt.setProvider(provider);
        importReceipt.setDescription(importReceiptRequest.getDescription());
        importReceipt.setImportDate(importReceiptRequest.getImportDate());
        ImportReceipt importReceipt1 = importReceiptRepository.save(importReceipt);
        return modelMapper.map(importReceipt1, ImportReceiptDto.class);
    }

    @Override
    public ImportReceiptDetailDto createImportReceiptDetai(
            Long importReceiptId, ImportReceiptDetailRequest importReceiptDetailRequest) {
        ImportReceipt importReceipt = importReceiptRepository.findById(importReceiptId).orElseThrow(
                ()->new ResourceNotFoundException("Import Receipt","id",importReceiptId)
        );

        return null;
    }
}
