package com.shvaiale.irishpub.integration.database.repository;

import com.shvaiale.irishpub.database.entity.FoodOrder;
import com.shvaiale.irishpub.database.repository.FoodOrderRepository;
import com.shvaiale.irishpub.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class FoodOrderRepositoryTest extends IntegrationTestBase {

    private final Integer WRONG_CUSTOMER_ID = 0;
    private final Integer CUSTOMER_ID = 1;
    private final LocalDateTime TIME = LocalDateTime.of(2019, 12, 20, 4,45);

    private final FoodOrderRepository foodOrderRepository;

    @Test
    void findByCustomerAndTime_Success() {
        Optional<FoodOrder> actualResult = foodOrderRepository.findBy(CUSTOMER_ID, TIME);

        assertThat(actualResult).isNotEmpty();
    }

    @Test
    void findByCustomerAndTime_NotFound() {
        Optional<FoodOrder> actualResult = foodOrderRepository.findBy(WRONG_CUSTOMER_ID, TIME);

        assertThat(actualResult).isEmpty();
    }
}