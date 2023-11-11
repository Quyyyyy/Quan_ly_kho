package com.example.quan_ly_kho.service;

import com.example.quan_ly_kho.dto.ProductDto;
import com.example.quan_ly_kho.dto.ResultResponse;
import com.example.quan_ly_kho.dto.request.ProductRequest;

public interface ProductService {
    ResultResponse getAllProduct(int pageNo, int pageSize, String sortBy, String sortDir);
    ProductDto createProduct(ProductRequest productRequest);
    ProductDto getProductById(Long productId);
    ProductDto updateProduct(Long productId,ProductRequest productRequest);
}
