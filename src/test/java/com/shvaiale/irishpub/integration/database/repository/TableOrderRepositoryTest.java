package com.shvaiale.irishpub.integration.database.repository;

import com.shvaiale.irishpub.database.entity.TableOrder;
import com.shvaiale.irishpub.database.repository.TableOrderRepository;
import com.shvaiale.irishpub.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class TableOrderRepositoryTest extends IntegrationTestBase {

    private final LocalDate EMPTY_DATE = LocalDate.of(2020, 1, 1);
    private final LocalDate DATE = LocalDate.of(2023, 9, 25);
    private final Integer TABLE_NUMBER = 10;
    private final LocalDateTime FREE_TIME = LocalDateTime.of(2023, 9, 25, 19, 0);
    private final LocalDateTime OCCUPIED_TIME = LocalDateTime.of(2023, 9, 25, 11, 45);

    private final TableOrderRepository tableOrderRepository;

    @Test
    void findAllByDate_Success() {
        List<TableOrder> orders = tableOrderRepository.findAllBy(DATE);

        assertThat(orders).isNotEmpty();
    }

    @Test
    void findAllByDate_NotFound() {
        List<TableOrder> orders = tableOrderRepository.findAllBy(EMPTY_DATE);

        assertThat(orders).isEmpty();
    }

    @Test
    void isBooked_NotBookedYet() {
        LocalDateTime hourAgo = FREE_TIME.minusHours(1);
        LocalDateTime hourAfter = FREE_TIME.plusHours(1);

        boolean result = tableOrderRepository.isBooked(TABLE_NUMBER, hourAgo, hourAfter);

        assertFalse(result);
    }

    @Test
    void isBooked_AlreadyBooked() {
        LocalDateTime hourAgo = OCCUPIED_TIME.minusHours(1);
        LocalDateTime hourAfter = OCCUPIED_TIME.plusHours(1);

        boolean result = tableOrderRepository.isBooked(TABLE_NUMBER, hourAgo, hourAfter);

        assertTrue(result);
    }
}