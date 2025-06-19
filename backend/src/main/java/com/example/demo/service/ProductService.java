package com.example.demo.service;

import com.example.demo.dto.product.ProductRequest;
import com.example.demo.dto.product.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductResponse createProduct(UUID shopId, ProductRequest request);

    ProductResponse updateProduct(UUID id, ProductRequest request);

    void deleteProduct(UUID id);

    ProductResponse getProductById(UUID id);

    List<ProductResponse> getProductsByShop(UUID shopId);

    Page<ProductResponse> getProductsByShopAndCategory(UUID shopId, String category, Pageable pageable);

    Page<ProductResponse> searchProducts(UUID shopId, String query, Pageable pageable);

    Page<ProductResponse> getAllProducts(Pageable pageable);

    List<ProductResponse> getAllProducts();
}