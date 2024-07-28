package com.shvaiale.irishpub.database.repository;

import com.shvaiale.irishpub.database.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepository extends JpaRepository<Dish, Integer> {
}
