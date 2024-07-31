package com.shvaiale.irishpub.service;

import com.shvaiale.irishpub.database.entity.Customer;
import com.shvaiale.irishpub.database.entity.Person;
import com.shvaiale.irishpub.database.entity.PersonalInformation;
import com.shvaiale.irishpub.database.repository.AddressRepository;
import com.shvaiale.irishpub.database.repository.CustomerRepository;
import com.shvaiale.irishpub.database.repository.PersonRepository;
import com.shvaiale.irishpub.database.repository.PersonalInformationRepository;
import com.shvaiale.irishpub.dto.CustomerCreateEditDto;
import com.shvaiale.irishpub.dto.CustomerReadDto;
import com.shvaiale.irishpub.mapper.CustomerCreateEditMapper;
import com.shvaiale.irishpub.mapper.CustomerReadMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
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
    private AddressRepository addressRepository;
    @Spy
    private CustomerReadMapper customerReadMapper;
    @Spy
    private CustomerCreateEditMapper customerCreateEditMapper;
    @InjectMocks
    private CustomerService customerService;

    @Test
    void findById_Success() {
        Customer customer = Customer.builder()
                .idPerson(CUSTOMER_ID)
                .name(NAME)
                .surname(SURNAME)
                .birthDate(BIRTH_DATE)
                .build();
        doReturn(Optional.of(customer)).when(customerRepository).findById(CUSTOMER_ID);
        CustomerReadDto expectedResult = new CustomerReadDto(CUSTOMER_ID, NAME, SURNAME, BIRTH_DATE);

        Optional<CustomerReadDto> actualResult = customerService.findById(CUSTOMER_ID);

        assertThat(actualResult).isNotEmpty();
        actualResult.ifPresent(actual -> assertEquals(expectedResult, actual));
        verify(customerRepository, only()).findById(CUSTOMER_ID);
        verify(customerReadMapper, only()).map(customer);
    }

    @Test
    void findById_CustomerNotFound() {
        doReturn(Optional.empty()).when(customerRepository).findById(CUSTOMER_ID);

        Optional<CustomerReadDto> actualResult = customerService.findById(CUSTOMER_ID);

        assertThat(actualResult).isEmpty();
        verify(customerRepository, only()).findById(CUSTOMER_ID);
    }

    @Test
    void create() {
        when(personRepository.save(any())).thenAnswer(invocation -> invocation.getArguments()[0]);
        when(customerRepository.save(any())).thenAnswer(invocation -> invocation.getArguments()[0]);
        CustomerCreateEditDto customerCreateEditDto = new CustomerCreateEditDto(BIRTH_DATE, NAME, SURNAME);
        CustomerReadDto expectedResult = new CustomerReadDto(null, NAME, SURNAME, BIRTH_DATE);

        // ID is not assigned, because repository is mocked
        CustomerReadDto actualResult = customerService.create(customerCreateEditDto);

        assertEquals(expectedResult, actualResult);
        verify(personRepository, only()).save(any());
        verify(customerRepository, only()).save(any());
        verify(customerReadMapper, only()).map(any());
    }

    @Test
    void attachPersonalInfo_Success() {
        Customer customer = Customer.builder()
                .idPerson(CUSTOMER_ID)
                .name(NAME)
                .surname(SURNAME)
                .birthDate(BIRTH_DATE)
                .build();
        doReturn(Optional.of(customer)).when(customerRepository).findById(CUSTOMER_ID);

        customerService.attachPersonalInfo(CUSTOMER_ID, PHONE_NUMBER, EMAIL);

        verify(customerRepository, times(1)).findById(CUSTOMER_ID);
        verify(customerRepository, times(1)).save(customer);
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    void attachPersonalInfo_CustomerNotFound() {
        doReturn(Optional.empty()).when(customerRepository).findById(CUSTOMER_ID);

        customerService.attachPersonalInfo(CUSTOMER_ID, PHONE_NUMBER, EMAIL);

        verify(customerRepository, only()).findById(CUSTOMER_ID);
    }

    @Test
    void attachAddress_Success() {
        PersonalInformation personalInformation = PersonalInformation.builder()
                .email(EMAIL)
                .phoneNumber(PHONE_NUMBER)
                .idCustomer(CUSTOMER_ID).build();
        doReturn(Optional.of(personalInformation)).when(personalInformationRepository).findById(CUSTOMER_ID);

        customerService.attachAddress(CUSTOMER_ID, HOUSE_NUMBER, STREET);

        verify(personalInformationRepository, only()).findById(CUSTOMER_ID);
        verify(addressRepository, only()).save(any());
    }

    @Test
    void attachAddress_PersonalInformationNotFound() {
        doReturn(Optional.empty()).when(personalInformationRepository).findById(CUSTOMER_ID);

        customerService.attachAddress(CUSTOMER_ID, HOUSE_NUMBER, STREET);

        verify(personalInformationRepository, only()).findById(CUSTOMER_ID);
        verifyNoInteractions(addressRepository);
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