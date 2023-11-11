package com.example.quan_ly_kho.controller;

import com.example.quan_ly_kho.dto.ProductDto;
import com.example.quan_ly_kho.dto.ResultResponse;
import com.example.quan_ly_kho.dto.request.ProductRequest;
import com.example.quan_ly_kho.service.ProductService;
import com.example.quan_ly_kho.utils.AppConstants;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/product")
public class ProductController {
    private ProductService productService;

    @GetMapping
    public ResultResponse getAllProducts(
            @RequestParam(value="pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
            @RequestParam(value="pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
            @RequestParam(value="sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,required = false) String sortDir
    ){
        return productService.getAllProduct(pageNo,pageSize,sortBy,sortDir);
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductRequest productRequest){
        ProductDto productDto1 = productService.createProduct(productRequest);
        return ResponseEntity.ok(productDto1);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long productId){
        ProductDto productDto = productService.getProductById(productId);
        return ResponseEntity.ok(productDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("id") Long productId,
                                                  @RequestBody ProductRequest productRequest){
        ProductDto productDto1 = productService.updateProduct(productId,productRequest);
        return ResponseEntity.ok(productDto1);
    }
}
