package com.example.spring_redis.controller;

import com.example.spring_redis.cache.ProductCache;
import com.example.spring_redis.model.GetProductResponseDTO;
import com.example.spring_redis.service.LoadProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final LoadProductService loadProductService;
    private final ProductCache productCache;

    @PostMapping("/load")
    public ResponseEntity<Integer> loadProduct(){
        return ResponseEntity.ok(loadProductService.run());
    }

    @GetMapping("/all")
    public ResponseEntity<GetProductResponseDTO> getAllProducts(){
        return ResponseEntity.ok(productCache.getProductCache());
    }
}
