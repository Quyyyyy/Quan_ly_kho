package com.example.quan_ly_kho.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImportReceiptDetailDto {
    private Long id;
    private ProductDto product;
    //private ImportReceiptDto importReceipt;
    private Long quantity;
    private Double price;
}
