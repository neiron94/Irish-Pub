package com.shvaiale.irishpub.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ServiceExecutor {

    private final TableOrderService tableOrderService;
    private final FoodOrderService foodOrderService;
    private final CustomerService customerService;


    public void bookTableTransaction() {
        boolean result;

        // Error: already booked
        System.out.println("Start first transaction.");
        try {
            tableOrderService.bookTable(43, LocalDateTime.of(2023, 9, 23, 1, 50), (short) 21, 22);
        }
        catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        // Error: wrong worker id
        System.out.println("Start second transaction.");
        try {
            tableOrderService.bookTable(43, LocalDateTime.of(2023, 9, 23, 1, 50), (short) 21, -1);
        }
        catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        // Error: wrong customer id
        System.out.println("Start third transaction.");
        try {
            tableOrderService.bookTable(-1, LocalDateTime.of(2023, 9, 23, 1, 50), (short) 21, 22);
        }
        catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        // Success
        System.out.println("Start fourth transaction.");
        try {
            tableOrderService.bookTable(43, LocalDateTime.of(2024, 5, 22, 15, 0), (short) 21, 22);
        }
        catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveNewCustomer() {
        LocalDate birthDate = LocalDate.of(1967,6,24);
        String name = "Maverick";
        String surname = "Liamkin";

        // Success
        System.out.println("Create new customer, attach personal info, attach two addresses.");
        customerService.addNewCustomer(birthDate, name, surname, null);
        int id = customerService.getCustomerId(birthDate, name, surname);
        customerService.attachPersonalInfo(id, "+353751303906", "liam.maver@example.com");
        customerService.attachAddress(id,40, "Margarita Mews");
        customerService.attachAddress(id,20, "Michelle Court");
    }

    public void attachInfoToExistingCustomer() {
        // Error: wrong customer id
        System.out.println("Start first operation.");
        customerService.attachPersonalInfo(-1, "+353751303906", "liam.maver@example.com");

        // Error: wrong customer id
        System.out.println("Start second operation.");
        customerService.attachAddress(-1, 20, "Michelle Court");

        // Success
        System.out.println("Start third operation.");
        customerService.attachPersonalInfo(32990, "+353751303915", "maxw.oconn@example.com");
        customerService.attachAddress(32990,11, "Carmel Ports");
    }

    public void deleteTableOrders() {
        LocalDate date = LocalDate.of(2014, 7, 13);

        tableOrderService.deleteTableOrdersByDate(date);
    }

    public void printFoodOrder() {
        // Error: order does not exist
        System.out.println("Start first operation.");
        foodOrderService.findByCustomerIdAndTime(30, LocalDateTime.of(2019,12,20, 4,45));

        // Success
        System.out.println("Start second operation. Offline food order.");
        foodOrderService.findByCustomerIdAndTime(31, LocalDateTime.of(2019,12,20, 4,45));

        // Success
        System.out.println("Start third operation. Online food order.");
        foodOrderService.findByCustomerIdAndTime(31, LocalDateTime.of(2019, 2, 16, 20, 27));
    }
}
