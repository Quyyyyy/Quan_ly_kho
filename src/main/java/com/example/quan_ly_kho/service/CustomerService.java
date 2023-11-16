package com.example.quan_ly_kho.service;

import com.example.quan_ly_kho.dto.CustomerDto;
import com.example.quan_ly_kho.dto.ResultResponse;

import java.util.List;

public interface CustomerService {
    ResultResponse getAllCustomer(int pageNo, int pageSize, String sortBy, String sortDir);
    CustomerDto createCustomer(CustomerDto customerDto);
    CustomerDto getCustomerById(Long customerId);
    CustomerDto updateCustomer(Long customerId,CustomerDto customerDto);
    List<CustomerDto> getCustomerss();
}
