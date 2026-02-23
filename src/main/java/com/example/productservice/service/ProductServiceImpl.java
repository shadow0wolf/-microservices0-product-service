package com.example.productservice.service;

import com.example.productservice.dto.ProductRequest;
import com.example.productservice.dto.ProductResponse;
import com.example.productservice.entity.Product;
import com.example.productservice.exception.ResourceNotFoundException;
import com.example.productservice.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProductResponse create(ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        repository.save(product);
        repository.flush();
        return new ProductResponse(product.getId(),  product.getName(), product.getPrice(), product.getStock());
    }

    @Override
    public ProductResponse getById(Long id){
        var product =   repository.findById(id).orElseThrow(()->new ResourceNotFoundException("Product not found"));
        return new ProductResponse(
                product.getId(), product.getName(), product.getPrice(), product.getStock()
        );
    }

    @Override
    public List<ProductResponse> getAll(){
        var products = repository.findAll();
        return products.stream().map(product->new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getStock())).collect(Collectors.toList());
    }

    @Override
    public ProductResponse update(Long productId , ProductRequest productRequest){
        var product = repository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product not found"));
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setStock(productRequest.getStock());
        repository.save(product);
        repository.flush();
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getStock());

    }

    @Override
    public void delete(Long productId){
        repository.deleteById(productId);
        repository.flush();
    }
}