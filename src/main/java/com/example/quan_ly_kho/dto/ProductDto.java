package com.example.quan_ly_kho.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private Long quantity;
    private Double price;
    private String description;
}
