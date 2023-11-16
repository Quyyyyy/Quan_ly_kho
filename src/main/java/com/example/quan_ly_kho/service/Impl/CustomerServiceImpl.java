package com.example.quan_ly_kho.service.Impl;

import com.example.quan_ly_kho.dto.CustomerDto;
import com.example.quan_ly_kho.dto.ResultResponse;
import com.example.quan_ly_kho.entity.Customer;
import com.example.quan_ly_kho.exception.ResourceNotFoundException;
import com.example.quan_ly_kho.repository.CustomerRepository;
import com.example.quan_ly_kho.service.CustomerService;
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
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;
    private ModelMapper modelMapper;

    @Override
    public ResultResponse getAllCustomer(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
        Page<Customer> customers = customerRepository.findAll(pageable);
        List<Customer> listOfCustomers = customers.getContent();
        List<CustomerDto> contents = listOfCustomers.stream()
                .map(customer -> modelMapper.map(customer,CustomerDto.class)).collect(Collectors.toList());

        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setContent(contents);
        resultResponse.setPageNo(customers.getNumber());
        resultResponse.setPageSize(customers.getSize());
        resultResponse.setTotalElements(customers.getTotalElements());
        resultResponse.setTotalPages(customers.getTotalPages());
        resultResponse.setLast(customers.isLast());
        return resultResponse;
    }

    @Override
    public CustomerDto createCustomer(CustomerDto customerDto) {
        Customer customer = modelMapper.map(customerDto,Customer.class);
        Customer saveCus = customerRepository.save(customer);
        CustomerDto customerDto1 = modelMapper.map(saveCus, CustomerDto.class);
        return customerDto1;
    }

    @Override
    public CustomerDto getCustomerById(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                ()->new ResourceNotFoundException("Customer","id",customerId)
        );
        CustomerDto customerDto = modelMapper.map(customer,CustomerDto.class);
        return customerDto;
    }

    @Override
    public CustomerDto updateCustomer(Long customerId, CustomerDto customerDto) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                ()->new ResourceNotFoundException("Customer","id",customerId)
        );
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setPhone(customerDto.getPhone());
        customer.setAddress(customerDto.getAddress());

        Customer saveCus = customerRepository.save(customer);
        return modelMapper.map(saveCus, CustomerDto.class);
    }

    @Override
    public List<CustomerDto> getCustomerss() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDto> customerDtos = customers.stream().
                map(customer -> modelMapper.map(customer, CustomerDto.class)).collect(Collectors.toList());
        return customerDtos;
    }
}
