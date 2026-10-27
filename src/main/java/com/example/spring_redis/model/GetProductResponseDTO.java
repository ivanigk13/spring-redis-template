package com.example.spring_redis.model;

import java.util.List;

public record GetProductResponseDTO(List<ProductDTO> products) {
}
