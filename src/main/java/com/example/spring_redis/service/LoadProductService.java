package com.example.spring_redis.service;

import com.example.spring_redis.entity.ProductEntity;
import com.example.spring_redis.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class LoadProductService {

    private final ProductRepository productRepository;

    public Integer run(){
        int counter = 0;
        for(int i = 1; i <= 1000; i++){
            ProductEntity entity = new ProductEntity();
            entity.setProductCode("productCode" + i);
            entity.setProductName("productName" + i);
            productRepository.save(entity);
            counter++;
        }
        log.info("{} products loaded", counter);
        return counter;
    }
}
