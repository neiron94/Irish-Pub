package com.shvaiale.irishpub.integration.service;

import com.shvaiale.irishpub.dto.CustomerCreateDto;
import com.shvaiale.irishpub.integration.IntegrationTestBase;
import com.shvaiale.irishpub.dto.CustomerDto;
import com.shvaiale.irishpub.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class CustomerServiceIT extends IntegrationTestBase {

    private final Integer WRONG_ID = 0;
    private final Integer CUSTOMER_ID = 1;
    private final LocalDate BIRTH_DATE = LocalDate.of(1995, 4, 22);
    private final String NAME = "Michael";
    private final String NEW_NAME = "Ales";
    private final String SURNAME = "O'Connor";

    private final CustomerService customerService;

    @Test
    void findById_Success() {
        Optional<CustomerDto> maybeCustomer = customerService.findById(CUSTOMER_ID);

        assertThat(maybeCustomer).isNotEmpty();
        maybeCustomer.ifPresent(customer -> assertEquals(CUSTOMER_ID, customer.id()));
    }

    @Test
    void findById_CustomerNotFound() {
        Optional<CustomerDto> maybeCustomer = customerService.findById(WRONG_ID);

        assertThat(maybeCustomer).isEmpty();
    }

    @Test
    void create_Success() {
        CustomerCreateDto customerCreateDto = new CustomerCreateDto(BIRTH_DATE, NEW_NAME, SURNAME);

        CustomerDto result = customerService.create(customerCreateDto);

        assertNotNull(result.id());
        Optional<CustomerDto> maybeCustomer = customerService.findById(result.id());
        assertThat(maybeCustomer).isNotEmpty();
    }

    @Test
    void create_NotCreated() {
        CustomerCreateDto customerCreateDto = new CustomerCreateDto(BIRTH_DATE, NAME, SURNAME);

        assertThrows(RuntimeException.class, () -> customerService.create(customerCreateDto));
    }

    //TODO
}