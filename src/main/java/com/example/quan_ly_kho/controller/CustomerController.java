package com.example.quan_ly_kho.controller;

import com.example.quan_ly_kho.dto.CustomerDto;
import com.example.quan_ly_kho.dto.ProviderDto;
import com.example.quan_ly_kho.dto.ResultResponse;
import com.example.quan_ly_kho.service.CustomerService;
import com.example.quan_ly_kho.service.ProviderService;
import com.example.quan_ly_kho.utils.AppConstants;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/user/customer")
public class CustomerController {
    private CustomerService customerService;

    @GetMapping
    public ResultResponse getAllCustomers(
            @RequestParam(value="pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
            @RequestParam(value="pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
            @RequestParam(value="sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,required = false) String sortDir
    ){
        return customerService.getAllCustomer(pageNo,pageSize,sortBy,sortDir);
    }

    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto){
        CustomerDto customerDto1 = customerService.createCustomer(customerDto);
        return ResponseEntity.ok(customerDto1);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable("id") Long customerId){
        CustomerDto customerDto = customerService.getCustomerById(customerId);
        return ResponseEntity.ok(customerDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable("id") Long customerId,
                                                    @RequestBody CustomerDto customerDto){
        CustomerDto customerDto1 = customerService.updateCustomer(customerId,customerDto);
        return ResponseEntity.ok(customerDto1);
    }
}
