package com.example.quan_ly_kho.service.Impl;

import com.example.quan_ly_kho.dto.ProductDto;
import com.example.quan_ly_kho.dto.ResultResponse;
import com.example.quan_ly_kho.dto.request.ProductRequest;
import com.example.quan_ly_kho.entity.Branch;
import com.example.quan_ly_kho.entity.Product;
import com.example.quan_ly_kho.exception.ResourceNotFoundException;
import com.example.quan_ly_kho.repository.BranchRepository;
import com.example.quan_ly_kho.repository.ProductRepository;
import com.example.quan_ly_kho.service.ProductService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private BranchRepository branchRepository;
    private ModelMapper modelMapper;

    @Override
    public List<ProductDto> getProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = products.stream().map((product)->modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
        return productDtos;
    }

    @Override
    public ResultResponse getAllProduct(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
        Page<Product> products = productRepository.findAll(pageable);
        List<Product> listOfProducts = products.getContent();
        List<ProductDto> contents = listOfProducts.stream()
                .map(product -> modelMapper.map(product,ProductDto.class)).collect(Collectors.toList());

        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setContent(contents);
        resultResponse.setPageNo(products.getNumber());
        resultResponse.setPageSize(products.getSize());
        resultResponse.setTotalElements(products.getTotalElements());
        resultResponse.setTotalPages(products.getTotalPages());
        resultResponse.setLast(products.isLast());
        return resultResponse;
    }

    @Override
    public ProductDto createProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setQuantity(productRequest.getQuantity());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        Product savePro = productRepository.save(product);
        ProductDto productDto1 = modelMapper.map(savePro, ProductDto.class);
        return productDto1;
    }

    @Override
    public ProductDto getProductById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                ()->new ResourceNotFoundException("Product","id",productId)
        );
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        return productDto;
    }

    @Override
    public ProductDto updateProduct(Long productId, ProductRequest productRequest) {
        Product product = productRepository.findById(productId).orElseThrow(
                ()->new ResourceNotFoundException("Product","id",productId)
        );

        product.setName(productRequest.getName());
        product.setQuantity(productRequest.getQuantity());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        Product savePro = productRepository.save(product);
        return modelMapper.map(savePro, ProductDto.class);
    }
}
