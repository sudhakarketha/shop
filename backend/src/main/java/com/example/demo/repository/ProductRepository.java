package com.example.demo.repository;

import com.example.demo.entity.Product;
import com.example.demo.entity.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByShop(Shop shop);

    Page<Product> findByShopAndCategory(Shop shop, String category, Pageable pageable);

    Page<Product> findByShopAndNameContainingIgnoreCase(Shop shop, String name, Pageable pageable);

    boolean existsByNameAndShop(String name, Shop shop);
}