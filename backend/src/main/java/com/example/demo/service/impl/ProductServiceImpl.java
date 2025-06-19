package com.example.demo.service.impl;

import com.example.demo.dto.product.ProductRequest;
import com.example.demo.dto.product.ProductResponse;
import com.example.demo.entity.Product;
import com.example.demo.entity.Shop;
import com.example.demo.entity.User;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import com.example.demo.service.ShopService;
import com.example.demo.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ShopService shopService;
    private final UserService userService;

    public ProductServiceImpl(ProductRepository productRepository,
            ShopService shopService,
            UserService userService) {
        this.productRepository = productRepository;
        this.shopService = shopService;
        this.userService = userService;
    }

    @Override
    public ProductResponse createProduct(UUID shopId, ProductRequest request) {
        Shop shop = getShopAndValidateOwnership(shopId);

        if (productRepository.existsByNameAndShop(request.getName(), shop)) {
            throw new RuntimeException("Product with this name already exists in the shop");
        }

        Product product = new Product();
        updateProductFromRequest(product, request);
        product.setShop(shop);

        Product savedProduct = productRepository.save(product);
        return convertToResponse(savedProduct);
    }

    @Override
    public ProductResponse updateProduct(UUID id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));

        validateShopOwnership(product.getShop());

        updateProductFromRequest(product, request);
        Product updatedProduct = productRepository.save(product);
        return convertToResponse(updatedProduct);
    }

    @Override
    public void deleteProduct(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));

        validateShopOwnership(product.getShop());
        productRepository.delete(product);
    }

    @Override
    public ProductResponse getProductById(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
        return convertToResponse(product);
    }

    @Override
    public List<ProductResponse> getProductsByShop(UUID shopId) {
        // Shop shop = shopService.getShopEntityById(shopId);
        Shop shop = getShopAndValidateOwnership(shopId);
        return productRepository.findByShop(shop).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProductResponse> getProductsByShopAndCategory(UUID shopId, String category, Pageable pageable) {
        Shop shop = getShopAndValidateOwnership(shopId);
        return productRepository.findByShopAndCategory(shop, category, pageable)
                .map(this::convertToResponse);
    }

    @Override
    public Page<ProductResponse> searchProducts(UUID shopId, String query, Pageable pageable) {
        Shop shop = getShopAndValidateOwnership(shopId);
        return productRepository.findByShopAndNameContainingIgnoreCase(shop, query, pageable)
                .map(this::convertToResponse);
    }

    @Override
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(this::convertToResponse);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private Shop getShopAndValidateOwnership(UUID shopId) {
        Shop shop = shopService.getShopEntityById(shopId);
        validateShopOwnership(shop);
        return shop;
    }

    private void validateShopOwnership(Shop shop) {
        User currentUser = userService.getCurrentUser();
        if (!shop.getOwner().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You don't have permission to manage products for this shop");
        }
    }

    private void updateProductFromRequest(Product product, ProductRequest request) {
        product.setName(request.getName());
        product.setCategory(request.getCategory());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setImageUrl(request.getImageUrl());
    }

    private ProductResponse convertToResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setCategory(product.getCategory());
        response.setPrice(product.getPrice());
        response.setStock(product.getStock());
        response.setImageUrl(product.getImageUrl());
        response.setShopId(product.getShop().getId());
        response.setCreatedAt(product.getCreatedAt());
        response.setUpdatedAt(product.getUpdatedAt());
        return response;
    }
}