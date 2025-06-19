package com.example.demo.controller;

import com.example.demo.dto.shop.ShopRequest;
import com.example.demo.dto.shop.ShopResponse;
import com.example.demo.service.ShopService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/shops")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ShopController {

    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @PostMapping
    @PreAuthorize("hasRole('SHOP_OWNER')")
    public ResponseEntity<ShopResponse> createShop(@Valid @RequestBody ShopRequest request) {
        return ResponseEntity.ok(shopService.createShop(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SHOP_OWNER')")
    public ResponseEntity<ShopResponse> updateShop(@PathVariable UUID id, @Valid @RequestBody ShopRequest request) {
        return ResponseEntity.ok(shopService.updateShop(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SHOP_OWNER')")
    public ResponseEntity<Void> deleteShop(@PathVariable UUID id) {
        shopService.deleteShop(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShopResponse> getShop(@PathVariable UUID id) {
        return ResponseEntity.ok(shopService.getShopById(id));
    }

    @GetMapping
    public ResponseEntity<Page<ShopResponse>> getAllShops(Pageable pageable) {
        return ResponseEntity.ok(shopService.getAllShops(pageable));
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<ShopResponse>> getShopsByOwner(@PathVariable UUID ownerId) {
        return ResponseEntity.ok(shopService.getShopsByOwner(ownerId));
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<Page<ShopResponse>> getShopsByCity(@PathVariable String city, Pageable pageable) {
        return ResponseEntity.ok(shopService.getShopsByCity(city, pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ShopResponse>> searchShops(@RequestParam String query, Pageable pageable) {
        return ResponseEntity.ok(shopService.searchShops(query, pageable));
    }
}