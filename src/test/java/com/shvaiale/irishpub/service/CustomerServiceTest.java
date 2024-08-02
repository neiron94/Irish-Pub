package com.shvaiale.irishpub.service;

import com.shvaiale.irishpub.database.entity.Person;
import com.shvaiale.irishpub.database.repository.CustomerRepository;
import com.shvaiale.irishpub.database.repository.PersonRepository;
import com.shvaiale.irishpub.database.repository.PersonalInformationRepository;
import com.shvaiale.irishpub.dto.CustomerReadDto;
import com.shvaiale.irishpub.mapper.CustomerCreateEditMapper;
import com.shvaiale.irishpub.mapper.CustomerReadMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    private final Integer CUSTOMER_ID = 1;
    private final String NAME = "Al";
    private final String SURNAME = "Shvaibovich";
    private final LocalDate BIRTH_DATE = LocalDate.of(2003, 7, 11);
    private final String PHONE_NUMBER = "+353234166900";
    private final String EMAIL = "owen.mraw@example.com";
    private final Integer HOUSE_NUMBER = 11;
    private final String STREET = "Gagarina";

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private PersonRepository personRepository;
    @Mock
    private PersonalInformationRepository personalInformationRepository;
    @Mock
    private CustomerReadMapper customerReadMapper;
    @Mock
    private CustomerCreateEditMapper customerCreateEditMapper;
    @InjectMocks
    private CustomerService customerService;

    @Test
    void findById_CustomerNotFound() {
        doReturn(Optional.empty()).when(customerRepository).findById(CUSTOMER_ID);

        Optional<CustomerReadDto> actualResult = customerService.findById(CUSTOMER_ID);

        assertThat(actualResult).isEmpty();
        verify(customerRepository, only()).findById(CUSTOMER_ID);
    }

    @Test
    void getCustomerId_Success() {
        Person person = Person.builder()
                .idPerson(CUSTOMER_ID)
                .name(NAME)
                .surname(SURNAME)
                .birthDate(BIRTH_DATE)
                .build();
        doReturn(Optional.of(person)).when(personRepository).findBy(BIRTH_DATE, NAME, SURNAME);

        Integer actualResult = customerService.getCustomerId(BIRTH_DATE, NAME, SURNAME);

        assertEquals(CUSTOMER_ID, actualResult);
        verify(personRepository,only()).findBy(BIRTH_DATE, NAME, SURNAME);
    }

    @Test
    void getCustomerId_PersonNotFound() {
        doReturn(Optional.empty()).when(personRepository).findBy(BIRTH_DATE, NAME, SURNAME);
        Integer expectedResult = -1;

        Integer actualResult = customerService.getCustomerId(BIRTH_DATE, NAME, SURNAME);

        assertEquals(expectedResult, actualResult);
        verify(personRepository,only()).findBy(BIRTH_DATE, NAME, SURNAME);
    }
}