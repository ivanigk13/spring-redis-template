package com.example.spring_redis.service;

import com.example.spring_redis.model.GetProductResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.same;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CacheServiceTest {

    @InjectMocks
    private CacheService cacheService;

    @Mock
    private GetProductService getProductService;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    @Test
    void getProductFromCache_cacheHit(){
        Object cached = new Object();
        GetProductResponseDTO expected = mock(GetProductResponseDTO.class);

        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get("products:all")).thenReturn(cached);
        when(objectMapper.convertValue(cached, GetProductResponseDTO.class)).thenReturn(expected);

        GetProductResponseDTO response = cacheService.getProductFromCache();
        assertEquals(expected, response);
        verify(getProductService, never()).run();
        verify(valueOperations, never()).set(anyString(), any(), any(Duration.class));
    }

    @Test
    void getProductFromCache_cacheMiss(){
        Object cached = new Object();
        GetProductResponseDTO expected = mock(GetProductResponseDTO.class);

        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get("products:all")).thenReturn(null);
        when(getProductService.run()).thenReturn(expected);

        GetProductResponseDTO response = cacheService.getProductFromCache();
        assertEquals(expected, response);
        verify(getProductService, times(1)).run();
        verify(valueOperations).set(eq("products:all"), same(expected), eq(Duration.ofMinutes(1)));
        verifyNoInteractions(objectMapper);
    }
}
