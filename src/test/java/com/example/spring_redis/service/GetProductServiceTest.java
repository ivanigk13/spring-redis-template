package com.example.spring_redis.service;

import com.example.spring_redis.cache.ProductCache;
import com.example.spring_redis.entity.ProductEntity;
import com.example.spring_redis.model.GetProductResponseDTO;
import com.example.spring_redis.model.ProductDTO;
import com.example.spring_redis.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetProductServiceTest {

    @InjectMocks
    private GetProductService getProductService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ObjectMapper objectMapper;

    @Test
    void run_shouldReturnProducts(){
        ProductEntity entity = new ProductEntity();
        ProductDTO productDTO = new ProductDTO();
        when(productRepository.findAll()).thenReturn(List.of(entity));
        when(objectMapper.convertValue(entity, ProductDTO.class)).thenReturn(productDTO);

        GetProductResponseDTO response = getProductService.run();
        assertNotNull(response);
        verify(productRepository, times(1)).findAll();
        verify(objectMapper, times(1)).convertValue(entity, ProductDTO.class);
    }

    @Test
    void run_shouldReturnEmptyList(){
        when(productRepository.findAll()).thenReturn(List.of());
        GetProductResponseDTO response = getProductService.run();
        assertNotNull(response);
        verify(productRepository, times(1)).findAll();
        verifyNoInteractions(objectMapper);
    }
}
