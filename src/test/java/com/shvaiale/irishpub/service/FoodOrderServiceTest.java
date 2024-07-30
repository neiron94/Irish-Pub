package com.shvaiale.irishpub.service;

import com.shvaiale.irishpub.database.entity.Customer;
import com.shvaiale.irishpub.database.entity.FoodOrder;
import com.shvaiale.irishpub.database.entity.Worker;
import com.shvaiale.irishpub.database.repository.FoodOrderRepository;
import com.shvaiale.irishpub.dto.FoodOrderDto;
import com.shvaiale.irishpub.mapper.FoodOrderMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.only;

@ExtendWith(MockitoExtension.class)
class FoodOrderServiceTest {

    private final Integer FOOD_ORDER_ID = 1;
    private final Integer CUSTOMER_ID = 2;
    private final Integer WORKER_ID = 3;
    private final Customer customer = Customer.builder()
            .idPerson(CUSTOMER_ID)
            .name("Al")
            .surname("Shvaibovich")
            .birthDate(LocalDate.of(2003, 7, 11))
            .build();
    private final Worker worker = Worker.builder()
            .idPerson(WORKER_ID)
            .email("worker@example.com")
            .workingPhoneNumber("+353234166900")
            .salary(5000)
            .build();
    private final LocalDateTime TIME = LocalDateTime.of(2019, 12, 20, 8, 20);

    @Mock
    private FoodOrderRepository foodOrderRepository;
    @Spy
    private FoodOrderMapper foodOrderMapper;
    @InjectMocks
    private FoodOrderService foodOrderService;

    @Test
    void findByCustomerIdAndTime_Success() {
        FoodOrder foodOrder = FoodOrder.builder()
                .id(FOOD_ORDER_ID)
                .customer(customer)
                .worker(worker)
                .time(TIME)
                .build();
        doReturn(Optional.of(foodOrder)).when(foodOrderRepository).findBy(CUSTOMER_ID, TIME);
        FoodOrderDto expectedResult = new FoodOrderDto(FOOD_ORDER_ID, CUSTOMER_ID, TIME, Collections.emptySet(), WORKER_ID);

        Optional<FoodOrderDto> actualResult = foodOrderService.findByCustomerIdAndTime(CUSTOMER_ID, TIME);

        assertThat(actualResult).isNotEmpty();
        actualResult.ifPresent(actual -> assertEquals(expectedResult, actual));
        verify(foodOrderRepository, only()).findBy(CUSTOMER_ID, TIME);
        verify(foodOrderMapper, only()).map(foodOrder);
    }

    @Test
    void findByCustomerIdAndTime_FoodOrderNotFound() {
        doReturn(Optional.empty()).when(foodOrderRepository).findBy(CUSTOMER_ID, TIME);

        Optional<FoodOrderDto> actualResult = foodOrderService.findByCustomerIdAndTime(CUSTOMER_ID, TIME);

        assertThat(actualResult).isEmpty();
        verify(foodOrderRepository, only()).findBy(CUSTOMER_ID, TIME);
    }
}