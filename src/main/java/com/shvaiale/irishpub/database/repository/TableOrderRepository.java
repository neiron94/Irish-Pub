package com.shvaiale.irishpub.database.repository;

import com.shvaiale.irishpub.database.entity.TableOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TableOrderRepository extends JpaRepository<TableOrder, Integer> {

    @Query("select t from TableOrder t where cast(t.time AS LocalDate) = :date")
    List<TableOrder> findAllBy(LocalDate date);

    @Query("SELECT COUNT(t) > 0 " +
            "FROM TableOrder t " +
            "WHERE t.tableNumber = :tableNumber AND t.time BETWEEN :hourAgo AND :hourAfter")
    boolean isBooked(int tableNumber, LocalDateTime hourAgo, LocalDateTime hourAfter);
}
