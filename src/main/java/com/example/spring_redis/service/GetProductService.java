package com.example.spring_redis.service;

import com.example.spring_redis.model.GetProductResponseDTO;
import com.example.spring_redis.model.ProductDTO;
import com.example.spring_redis.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class GetProductService {

    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    public GetProductResponseDTO run(){
        List<ProductDTO> products = productRepository.findAll().stream()
            .map(entity -> objectMapper.convertValue(entity, ProductDTO.class))
            .toList();
        return new GetProductResponseDTO(products);
    }
}
