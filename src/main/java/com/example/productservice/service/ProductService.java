package com.example.productservice.service;

import com.example.productservice.dto.ProductRequest;
import com.example.productservice.dto.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse create(ProductRequest request);

    ProductResponse getById(Long id);

    List<ProductResponse> getAll();

    ProductResponse update(Long id, ProductRequest request);

    void delete(Long id);
}