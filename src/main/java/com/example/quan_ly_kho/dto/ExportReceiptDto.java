package com.example.quan_ly_kho.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExportReceiptDto {
    private Long id;
    private String description;
    private UserDto user;
    private CustomerDto customer;
}
