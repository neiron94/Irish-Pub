package com.shvaiale.irishpub.dto;

import com.shvaiale.irishpub.database.entity.Dish;

import java.time.LocalDateTime;
import java.util.Set;

public record FoodOrderDto(Integer id, Integer customerId, LocalDateTime time, Set<Dish> dishes, Integer workerId) {
}
