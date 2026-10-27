package com.example.spring_redis.service;

import com.example.spring_redis.model.GetProductResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;

@Service
@Log4j2
@RequiredArgsConstructor
public class CacheService {

    private static final String PRODUCT_CACHE_KEY = "products:all";

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;
    private final GetProductService getProductService;

    public GetProductResponseDTO getProductFromCache() {
        Object cached = redisTemplate.opsForValue()
            .get(PRODUCT_CACHE_KEY);

        if (cached != null) {
            log.info("Cache hit: {}", PRODUCT_CACHE_KEY);
            return objectMapper.convertValue(cached, GetProductResponseDTO.class);
        }
        log.info("Cache miss: {}", PRODUCT_CACHE_KEY);

        GetProductResponseDTO response = getProductService.run();

        redisTemplate.opsForValue().set(
            PRODUCT_CACHE_KEY,
            response,
            Duration.ofMinutes(1)
        );
        return response;
    }
}
