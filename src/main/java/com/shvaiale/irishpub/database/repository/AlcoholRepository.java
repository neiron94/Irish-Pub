package com.shvaiale.irishpub.database.repository;

import com.shvaiale.irishpub.database.entity.Alcohol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlcoholRepository extends JpaRepository<Alcohol, Integer> {
}
