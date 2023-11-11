package com.example.quan_ly_kho.dto.request;

import com.example.quan_ly_kho.dto.ImportReceiptDto;
import com.example.quan_ly_kho.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImportReceiptDetailRequest {
    private Long id;
    private Long productId;
    private Long quantity;
    private Double price;
}
