package com.shvaiale.irishpub.database.repository;

import com.shvaiale.irishpub.database.entity.FoodOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface FoodOrderRepository extends JpaRepository<FoodOrder, Integer> {

    @Query("select fo from FoodOrder fo where fo.customer = :customerId and fo.time = :time")
    Optional<FoodOrder> findBy(Integer customerId, LocalDateTime time);
}
