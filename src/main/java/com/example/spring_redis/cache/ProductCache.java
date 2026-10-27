package com.example.spring_redis.cache;

import com.example.spring_redis.model.GetProductResponseDTO;
import com.example.spring_redis.service.CacheService;
import com.example.spring_redis.service.GetProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Log4j2
@RequiredArgsConstructor
public class ProductCache {

    @Value("${is-redis-active:false}")
    private boolean isRedisActive;

    private final GetProductService getProductService;
    private final CacheService cacheService;

    public GetProductResponseDTO getProductCache() {
        LocalDateTime startTime = LocalDateTime.now();
        long startNanos = System.nanoTime();

        GetProductResponseDTO response;

        if (isRedisActive) {
            log.info("Fetching products through Redis cache...");
            response = cacheService.getProductFromCache();
        } else {
            log.info("Redis disabled. Fetching products from database...");
            response = getProductService.run();
        }

        long elapsedNanos = System.nanoTime() - startNanos;
        LocalDateTime endTime = LocalDateTime.now();

        log.info("Start time: {}", startTime);
        log.info("End time: {}", endTime);
        log.info("Elapsed time: {} ms", elapsedNanos / 1_000_000.0);

        return response;
    }

}
