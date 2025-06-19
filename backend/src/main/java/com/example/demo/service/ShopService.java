package com.example.demo.service;

import com.example.demo.dto.shop.ShopRequest;
import com.example.demo.dto.shop.ShopResponse;
import com.example.demo.entity.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ShopService {
    ShopResponse createShop(ShopRequest request);

    ShopResponse updateShop(UUID id, ShopRequest request);

    void deleteShop(UUID id);

    ShopResponse getShopById(UUID id);

    Shop getShopEntityById(UUID id);

    Page<ShopResponse> getAllShops(Pageable pageable);

    List<ShopResponse> getShopsByOwner(UUID ownerId);

    Page<ShopResponse> getShopsByCity(String city, Pageable pageable);

    Page<ShopResponse> searchShops(String query, Pageable pageable);
}