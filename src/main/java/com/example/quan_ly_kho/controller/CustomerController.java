package com.example.quan_ly_kho.controller;

import com.example.quan_ly_kho.dto.CustomerDto;
import com.example.quan_ly_kho.dto.ResultResponse;
import com.example.quan_ly_kho.service.CustomerService;
import com.example.quan_ly_kho.utils.AppConstants;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user/customer")
public class CustomerController {
    private CustomerService customerService;

    @GetMapping("/all")
    public ResponseEntity<List<CustomerDto>> getCustomers(){
        List<CustomerDto> dtoList = customerService.getCustomerss();
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping
    public ResponseEntity<ResultResponse> getAllCustomers(
            @RequestParam(value="pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
            @RequestParam(value="pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
            @RequestParam(value="sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,required = false) String sortDir
    ){
        ResultResponse rs = customerService.getAllCustomer(pageNo,pageSize,sortBy,sortDir);
        return ResponseEntity.ok(rs);
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
