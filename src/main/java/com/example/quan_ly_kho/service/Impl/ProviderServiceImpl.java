package com.example.quan_ly_kho.service.Impl;

import com.example.quan_ly_kho.dto.ProductDto;
import com.example.quan_ly_kho.dto.ProviderDto;
import com.example.quan_ly_kho.dto.ResultResponse;
import com.example.quan_ly_kho.entity.Product;
import com.example.quan_ly_kho.entity.Provider;
import com.example.quan_ly_kho.exception.ResourceNotFoundException;
import com.example.quan_ly_kho.repository.ProviderRepository;
import com.example.quan_ly_kho.service.ProviderService;
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
public class ProviderServiceImpl implements ProviderService {
    private ProviderRepository providerRepository;
    private ModelMapper modelMapper;

    @Override
    public List<ProviderDto> getProviders() {
        List<Provider> providers = providerRepository.findAll();
        List<ProviderDto> providerDtos = providers.stream().map((provider)->modelMapper.map(provider, ProviderDto.class))
                .collect(Collectors.toList());
        return providerDtos;
    }

    @Override
    public ResultResponse getAllProvider(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
        Page<Provider> providers = providerRepository.findAll(pageable);
        List<Provider> listOfProviders = providers.getContent();
        List<ProviderDto> contents = listOfProviders.stream()
                .map(provider -> modelMapper.map(provider,ProviderDto.class)).collect(Collectors.toList());

        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setContent(contents);
        resultResponse.setPageNo(providers.getNumber());
        resultResponse.setPageSize(providers.getSize());
        resultResponse.setTotalElements(providers.getTotalElements());
        resultResponse.setTotalPages(providers.getTotalPages());
        resultResponse.setLast(providers.isLast());
        return resultResponse;
    }

    @Override
    public ProviderDto createProvider(ProviderDto providerDto) {
        Provider provider = modelMapper.map(providerDto,Provider.class);
        Provider savePro = providerRepository.save(provider);
        ProviderDto providerDto1 = modelMapper.map(savePro, ProviderDto.class);
        return providerDto1;
    }

    @Override
    public ProviderDto getProviderById(Long providerId) {
        Provider provider = providerRepository.findById(providerId).orElseThrow(
                ()->new ResourceNotFoundException("Provider","id",providerId)
        );
        ProviderDto providerDto = modelMapper.map(provider, ProviderDto.class);
        return providerDto;
    }

    @Override
    public ProviderDto updateProvider(Long providerId, ProviderDto providerDto) {
        Provider provider = providerRepository.findById(providerId).orElseThrow(
                ()->new ResourceNotFoundException("Provider","id",providerId)
        );
        provider.setName(providerDto.getName());
        provider.setEmail(providerDto.getEmail());
        provider.setPhone(providerDto.getPhone());
        provider.setAddress(providerDto.getAddress());

        Provider savePro = providerRepository.save(provider);
        return modelMapper.map(savePro, ProviderDto.class);
    }
}
