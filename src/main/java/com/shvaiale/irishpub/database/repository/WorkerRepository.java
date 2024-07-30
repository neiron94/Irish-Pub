package com.shvaiale.irishpub.database.repository;

import com.shvaiale.irishpub.database.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<Worker, Integer> {
}
