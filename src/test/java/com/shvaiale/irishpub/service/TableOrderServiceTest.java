package com.shvaiale.irishpub.service;

import com.shvaiale.irishpub.database.entity.Customer;
import com.shvaiale.irishpub.database.entity.TableOrder;
import com.shvaiale.irishpub.database.entity.Worker;
import com.shvaiale.irishpub.database.repository.CustomerRepository;
import com.shvaiale.irishpub.database.repository.TableOrderRepository;
import com.shvaiale.irishpub.database.repository.WorkerRepository;
import com.shvaiale.irishpub.exception.AlreadyBookedException;
import com.shvaiale.irishpub.exception.CustomerNotFoundException;
import com.shvaiale.irishpub.exception.WorkerNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TableOrderServiceTest {

    private final LocalDate DATE = LocalDate.of(2015, 11, 11);
    private final LocalDateTime DATE_TIME = LocalDateTime.of(DATE, LocalTime.of(10, 10, 0));
    private final LocalDateTime HOUR_AGO = DATE_TIME.minusHours(1);
    private final LocalDateTime HOUR_AFTER = DATE_TIME.plusHours(1);
    private final Short TABLE_NUMBER = 1;
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

    @Mock
    private TableOrderRepository tableOrderRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private WorkerRepository workerRepository;
    @InjectMocks
    private TableOrderService tableOrderService;

    @Test
    void deleteTableOrdersByDate() {
        List<TableOrder> emptyList = Collections.emptyList();
        doReturn(emptyList).when(tableOrderRepository).findAllBy(DATE);

        tableOrderService.deleteTableOrdersByDate(DATE);

        verify(tableOrderRepository, times(1)).findAllBy(DATE);
        verify(tableOrderRepository, times(1)).deleteAll(emptyList);
        verifyNoMoreInteractions(tableOrderRepository);
    }

    @Test
    void isBooked() {
        tableOrderService.isBooked(DATE_TIME, TABLE_NUMBER);

        verify(tableOrderRepository, only()).isBooked(TABLE_NUMBER, HOUR_AGO, HOUR_AFTER);
    }

    @Test
    void bookTable_Success() {
        doReturn(Optional.of(customer)).when(customerRepository).findById(CUSTOMER_ID);
        doReturn(Optional.of(worker)).when(workerRepository).findById(WORKER_ID);
        doReturn(false).when(tableOrderRepository).isBooked(TABLE_NUMBER, HOUR_AGO, HOUR_AFTER);

        tableOrderService.bookTable(CUSTOMER_ID, DATE_TIME, TABLE_NUMBER, WORKER_ID);

        verify(customerRepository, only()).findById(CUSTOMER_ID);
        verify(workerRepository, only()).findById(WORKER_ID);
        verify(tableOrderRepository, times(1)).isBooked(TABLE_NUMBER, HOUR_AGO, HOUR_AFTER);
        verify(tableOrderRepository, times(1)).save(any());
        verifyNoMoreInteractions(tableOrderRepository);
    }

    @Test
    void bookTable_CustomerNotFound() {
        doReturn(Optional.empty()).when(customerRepository).findById(CUSTOMER_ID);

        assertThrows(CustomerNotFoundException.class, () -> tableOrderService.bookTable(CUSTOMER_ID, DATE_TIME, TABLE_NUMBER, WORKER_ID));

        verify(customerRepository, only()).findById(CUSTOMER_ID);
        verifyNoInteractions(workerRepository);
        verifyNoInteractions(tableOrderRepository);
    }

    @Test
    void bookTable_WorkerNotFound() {
        doReturn(Optional.of(customer)).when(customerRepository).findById(CUSTOMER_ID);
        doReturn(Optional.empty()).when(workerRepository).findById(WORKER_ID);

        assertThrows(WorkerNotFoundException.class, () -> tableOrderService.bookTable(CUSTOMER_ID, DATE_TIME, TABLE_NUMBER, WORKER_ID));

        verify(customerRepository, only()).findById(CUSTOMER_ID);
        verify(workerRepository, only()).findById(WORKER_ID);
        verifyNoInteractions(tableOrderRepository);
    }

    @Test
    void bookTable_AlreadyBooked() {
        doReturn(Optional.of(customer)).when(customerRepository).findById(CUSTOMER_ID);
        doReturn(Optional.of(worker)).when(workerRepository).findById(WORKER_ID);
        doReturn(true).when(tableOrderRepository).isBooked(TABLE_NUMBER, HOUR_AGO, HOUR_AFTER);

        assertThrows(AlreadyBookedException.class, () -> tableOrderService.bookTable(CUSTOMER_ID, DATE_TIME, TABLE_NUMBER, WORKER_ID));

        verify(customerRepository, only()).findById(CUSTOMER_ID);
        verify(workerRepository, only()).findById(WORKER_ID);
        verify(tableOrderRepository, only()).isBooked(TABLE_NUMBER, HOUR_AGO, HOUR_AFTER);
    }
}