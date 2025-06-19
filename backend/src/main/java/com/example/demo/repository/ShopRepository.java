package com.example.demo.repository;

import com.example.demo.entity.Shop;
import com.example.demo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ShopRepository extends JpaRepository<Shop, UUID> {
    List<Shop> findByOwner(User owner);

    Page<Shop> findByCity(String city, Pageable pageable);

    Page<Shop> findByNameContainingIgnoreCase(String name, Pageable pageable);

    boolean existsByNameAndOwner(String name, User owner);
}