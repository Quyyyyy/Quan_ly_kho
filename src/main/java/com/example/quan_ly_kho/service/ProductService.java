package com.example.quan_ly_kho.service;

import com.example.quan_ly_kho.dto.ProductDto;
import com.example.quan_ly_kho.dto.ResultResponse;
import com.example.quan_ly_kho.dto.request.ProductRequest;

import java.util.List;

public interface ProductService {
    List<ProductDto> getProducts();
    ResultResponse getAllProduct(int pageNo, int pageSize, String sortBy, String sortDir);
    ProductDto createProduct(ProductRequest productRequest);
    ProductDto getProductById(Long productId);
    ProductDto updateProduct(Long productId,ProductRequest productRequest);
}
