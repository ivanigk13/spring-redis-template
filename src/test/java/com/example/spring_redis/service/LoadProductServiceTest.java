package com.example.spring_redis.service;

import com.example.spring_redis.entity.ProductEntity;
import com.example.spring_redis.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoadProductServiceTest {

    @InjectMocks
    private LoadProductService loadProductService;

    @Mock
    private ProductRepository productRepository;

    @Test
    void run_shouldLoadProduct1000() {
        Integer total = loadProductService.run();
        assertEquals(1000, total);
        verify(productRepository, times(1000)).save(any(ProductEntity.class));
    }
}
