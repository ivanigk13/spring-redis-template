package com.example.spring_redis.cache;
import com.example.spring_redis.model.GetProductResponseDTO;
import com.example.spring_redis.service.CacheService;
import com.example.spring_redis.service.GetProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductCacheTest {

    @InjectMocks
    private ProductCache productCache;

    @Mock
    private GetProductService getProductService;

    @Mock
    private CacheService cacheService;

    @Test
    void getProductCache_redisActive(){
        GetProductResponseDTO expected = mock(GetProductResponseDTO.class);
        ReflectionTestUtils.setField(productCache,"isRedisActive",true);
        when(productCache.getProductCache()).thenReturn(expected);
        GetProductResponseDTO response = productCache.getProductCache();

        assertEquals(expected,response);
        verify(cacheService, times(1)).getProductFromCache();
        verify(getProductService, never()).run();
    }

    @Test
    void getProductCache_redisDisabled(){
        GetProductResponseDTO expected = mock(GetProductResponseDTO.class);
        ReflectionTestUtils.setField(productCache,"isRedisActive",false);
        when(productCache.getProductCache()).thenReturn(expected);
        GetProductResponseDTO response = productCache.getProductCache();

        assertEquals(expected,response);
        verify(getProductService, times(1)).run();
        verifyNoInteractions(cacheService);
    }

}
