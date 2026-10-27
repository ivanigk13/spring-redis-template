package com.example.spring_redis.controller;

import com.example.spring_redis.cache.ProductCache;
import com.example.spring_redis.model.GetProductResponseDTO;
import com.example.spring_redis.service.LoadProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private LoadProductService loadProductService;

    @Mock
    private ProductCache productCache;

    @Test
    void loadProduct() {
        when(loadProductService.run()).thenReturn(1000);
        ResponseEntity<Integer> response = productController.loadProduct();
        assertEquals(200, response.getStatusCode().value());
        assertEquals(1000, response.getBody());
        verify(loadProductService).run();
    }

    @Test
    void getProduct() {
        GetProductResponseDTO expected = mock(GetProductResponseDTO.class);
        when(productCache.getProductCache()).thenReturn(expected);
        ResponseEntity<GetProductResponseDTO> response = productController.getAllProducts();
        assertEquals(200, response.getStatusCode().value());
        assertEquals(expected, response.getBody());
        verify(productCache).getProductCache();
    }

}
