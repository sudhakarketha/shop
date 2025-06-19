package com.example.demo.controller;

import com.example.demo.dto.product.ProductRequest;
import com.example.demo.dto.product.ProductResponse;
import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

//    @GetMapping("/products")
//    public ResponseEntity<Page<ProductResponse>> getAllProducts(Pageable pageable) {
//        return ResponseEntity.ok(productService.getAllProducts(pageable));
//    }
    
    @GetMapping("/products")
    public ResponseEntity<Map<String, List<ProductResponse>>> getAllProducts() {
        List<ProductResponse> products = productService.getAllProducts();
        Map<String, List<ProductResponse>> response = new HashMap<>();
        response.put("products", products);
        return ResponseEntity.ok(response);
    }
    

    @PostMapping("/shops/{shopId}/products")
    @PreAuthorize("hasRole('SHOP_OWNER')")
    public ResponseEntity<ProductResponse> createProduct(
            @PathVariable UUID shopId,
            @Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.createProduct(shopId, request));
    }

    @PutMapping("/shops/{shopId}/products/{id}")
    @PreAuthorize("hasRole('SHOP_OWNER')")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable UUID shopId,
            @PathVariable UUID id,
            @Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    @DeleteMapping("/shops/{shopId}/products/{id}")
    @PreAuthorize("hasRole('SHOP_OWNER')")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID shopId, @PathVariable UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/shops/{shopId}/products/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable UUID shopId, @PathVariable UUID id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/shops/{shopId}/products")
    public ResponseEntity<List<ProductResponse>> getProductsByShop(@PathVariable UUID shopId) {
        return ResponseEntity.ok(productService.getProductsByShop(shopId));
    }

    @GetMapping("/shops/{shopId}/products/category/{category}")
    public ResponseEntity<Page<ProductResponse>> getProductsByCategory(
            @PathVariable UUID shopId,
            @PathVariable String category,
            Pageable pageable) {
        return ResponseEntity.ok(productService.getProductsByShopAndCategory(shopId, category, pageable));
    }

    @GetMapping("/shops/{shopId}/products/search")
    public ResponseEntity<Page<ProductResponse>> searchProducts(
            @PathVariable UUID shopId,
            @RequestParam String query,
            Pageable pageable) {
        return ResponseEntity.ok(productService.searchProducts(shopId, query, pageable));
    }
}