package com.shvaiale.irishpub.database.repository;

import com.shvaiale.irishpub.database.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Integer> {
}
