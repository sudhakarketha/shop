package com.example.demo.service.impl;

import com.example.demo.dto.shop.ShopRequest;
import com.example.demo.dto.shop.ShopResponse;
import com.example.demo.entity.Shop;
import com.example.demo.entity.User;
import com.example.demo.repository.ShopRepository;
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
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;
    private final UserService userService;

    public ShopServiceImpl(ShopRepository shopRepository, UserService userService) {
        this.shopRepository = shopRepository;
        this.userService = userService;
    }

    @Override
    public ShopResponse createShop(ShopRequest request) {
        User currentUser = userService.getCurrentUser();

        if (shopRepository.existsByNameAndOwner(request.getName(), currentUser)) {
            throw new RuntimeException("Shop with this name already exists for the owner");
        }

        Shop shop = new Shop();
        updateShopFromRequest(shop, request);
        shop.setOwner(currentUser);

        Shop savedShop = shopRepository.save(shop);
        return convertToResponse(savedShop);
    }

    @Override
    public ShopResponse updateShop(UUID id, ShopRequest request) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Shop not found with id: " + id));

        User currentUser = userService.getCurrentUser();
        if (!shop.getOwner().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You don't have permission to update this shop");
        }

        updateShopFromRequest(shop, request);
        Shop updatedShop = shopRepository.save(shop);
        return convertToResponse(updatedShop);
    }

    @Override
    public void deleteShop(UUID id) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Shop not found with id: " + id));

        User currentUser = userService.getCurrentUser();
        if (!shop.getOwner().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You don't have permission to delete this shop");
        }

        shopRepository.delete(shop);
    }

    @Override
    public ShopResponse getShopById(UUID id) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Shop not found with id: " + id));
        return convertToResponse(shop);
    }

    @Override
    public Page<ShopResponse> getAllShops(Pageable pageable) {
        return shopRepository.findAll(pageable)
                .map(this::convertToResponse);
    }

//    @Override
//    public List<ShopResponse> getShopsByOwner(UUID ownerId) {
//        User owner = userService.getUserById(ownerId);
//        return shopRepository.findByOwner(owner).stream()
//                .map(this::convertToResponse)
//                .collect(Collectors.toList());
//    }
    
    @Override
    public List<ShopResponse> getShopsByOwner(UUID ownerId) {
        User owner = userService.getUserById(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + ownerId));
        return shopRepository.findByOwner(owner).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }


    @Override
    public Page<ShopResponse> getShopsByCity(String city, Pageable pageable) {
        return shopRepository.findByCity(city, pageable)
                .map(this::convertToResponse);
    }

    @Override
    public Page<ShopResponse> searchShops(String query, Pageable pageable) {
        return shopRepository.findByNameContainingIgnoreCase(query, pageable)
                .map(this::convertToResponse);
    }

    @Override
    public Shop getShopEntityById(UUID id) {
        return shopRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Shop not found with id: " + id));
    }

    private void updateShopFromRequest(Shop shop, ShopRequest request) {
        shop.setName(request.getName());
        shop.setOwnerName(request.getOwnerName());
        shop.setEmail(request.getEmail());
        shop.setPhone(request.getPhone());
        shop.setAddress(request.getAddress());
        shop.setCity(request.getCity());
        shop.setState(request.getState());
        shop.setZipCode(request.getZipCode());
        shop.setDescription(request.getDescription());
        shop.setOpenHours(request.getOpenHours());
        shop.setLatitude(request.getLatitude());
        shop.setLongitude(request.getLongitude());
    }

    private ShopResponse convertToResponse(Shop shop) {
        ShopResponse response = new ShopResponse();
        response.setId(shop.getId());
        response.setName(shop.getName());
        response.setOwnerName(shop.getOwnerName());
        response.setEmail(shop.getEmail());
        response.setPhone(shop.getPhone());
        response.setAddress(shop.getAddress());
        response.setCity(shop.getCity());
        response.setState(shop.getState());
        response.setZipCode(shop.getZipCode());
        response.setDescription(shop.getDescription());
        response.setOpenHours(shop.getOpenHours());
        response.setLatitude(shop.getLatitude());
        response.setLongitude(shop.getLongitude());
        response.setOwnerId(shop.getOwner().getId());
        response.setCreatedAt(shop.getCreatedAt());
        response.setUpdatedAt(shop.getUpdatedAt());
        return response;
    }
}