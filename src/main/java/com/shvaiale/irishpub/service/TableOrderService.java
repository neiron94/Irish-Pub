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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TableOrderService {

    private final TableOrderRepository tableOrderRepository;
    private final CustomerRepository customerRepository;
    private final WorkerRepository workerRepository;

    public void deleteTableOrdersByDate(LocalDate date) {
        List<TableOrder> tableOrders = tableOrderRepository.findAllBy(date);
        tableOrderRepository.deleteAll(tableOrders);
        log.info("All table orders with date={} are deleted.", date);
    }

    @Transactional(readOnly = true)
    public boolean isBooked(LocalDateTime orderTime, int tableNumber) {
        LocalDateTime hourAgo = orderTime.minusHours(1);
        LocalDateTime hourAfter = orderTime.plusHours(1);
        return tableOrderRepository.isBooked(tableNumber, hourAgo, hourAfter);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void bookTable(int idCustomer, LocalDateTime orderTime, short tableNumber, int idWorker) {
        Optional<Customer> maybeCustomer = customerRepository.findById(idCustomer);
        Customer customer = maybeCustomer.orElseThrow(() -> new CustomerNotFoundException(idCustomer));
        log.info("Customer with id={} is found.", idCustomer);

        Optional<Worker> maybeWorker = workerRepository.findById(idWorker);
        Worker worker = maybeWorker.orElseThrow(() -> new WorkerNotFoundException("Worker with id=%d is not found.".formatted(idWorker)));
        log.info("Worker with id={} is found.", idWorker);

        if (isBooked(orderTime, tableNumber))
            throw new AlreadyBookedException("Table №%d for time %s is already booked.".formatted(tableNumber, orderTime));
        log.info("Table №{} for time {} is not booked.", tableNumber, orderTime);

        TableOrder tableOrder = TableOrder.builder()
                .customer(customer)
                .time(orderTime)
                .tableNumber(tableNumber)
                .worker(worker)
                .build();
        tableOrderRepository.save(tableOrder);
        log.info("Table №{} for time {} for customer with id={} is booked by worker with id={}.", tableNumber, orderTime, idCustomer, idWorker);
    }
}
