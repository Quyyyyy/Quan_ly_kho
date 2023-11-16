package com.example.quan_ly_kho.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExportReceiptDto {
    private Long id;
    private Date importDate;
    private String description;
    private UserDto user;
    private CustomerDto customer;
    private List<ExportReceiptDetailDto> listExportReceiptDetails;
}
