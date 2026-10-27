package com.example.spring_redis.entity;

import jakarta.persistence.PrePersist;

import java.time.LocalDateTime;

public class ProductListener {

    @PrePersist
    public void prePersist(ProductEntity product) {
        product.setCreateTime(LocalDateTime.now());
    }
}
