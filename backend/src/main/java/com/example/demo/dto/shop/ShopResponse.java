package com.example.demo.dto.shop;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ShopResponse {
    private UUID id;
    private String name;
    private String ownerName;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String description;
    private String imageUrl;
    private String openHours;
    private Double latitude;
    private Double longitude;
    private UUID ownerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}