package com.shvaiale.irishpub.service;

import com.querydsl.core.types.Predicate;
import com.shvaiale.irishpub.database.entity.Customer;
import com.shvaiale.irishpub.database.entity.Person;
import com.shvaiale.irishpub.database.entity.PersonalInformation;
import com.shvaiale.irishpub.database.querydsl.QPredicates;
import com.shvaiale.irishpub.database.repository.CustomerRepository;
import com.shvaiale.irishpub.database.repository.PersonRepository;
import com.shvaiale.irishpub.database.repository.PersonalInformationRepository;
import com.shvaiale.irishpub.dto.CustomerCreateEditDto;
import com.shvaiale.irishpub.dto.CustomerFilter;
import com.shvaiale.irishpub.dto.CustomerReadDto;
import com.shvaiale.irishpub.dto.PersonReadDto;
import com.shvaiale.irishpub.mapper.CustomerCreateEditMapper;
import com.shvaiale.irishpub.mapper.CustomerReadMapper;
import com.shvaiale.irishpub.mapper.PersonReadMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.shvaiale.irishpub.database.entity.QCustomer.customer;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PersonRepository personRepository;
    private final CustomerReadMapper customerReadMapper;
    private final CustomerCreateEditMapper customerCreateEditMapper;
    private final PersonReadMapper personReadMapper;

    @Transactional(readOnly = true)
    public Page<CustomerReadDto> findAll(CustomerFilter filter, Pageable pageable) {
        Predicate predicate = QPredicates.builder()
                .add(filter.name(), customer.name::containsIgnoreCase)
                .add(filter.surname(), customer.surname::containsIgnoreCase)
                .add(filter.birthDate(), customer.birthDate::before)
                .build();

        return customerRepository.findAll(predicate, pageable).map(customerReadMapper::map);
    }

    @Transactional(readOnly = true)
    public List<CustomerReadDto> findAll() {
        return customerRepository.findAll()
                .stream()
                .map(customerReadMapper::map)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<CustomerReadDto> findById(Integer id) {
        Optional<Customer> maybeCustomer = customerRepository.findById(id);

        maybeCustomer.ifPresentOrElse(
                customer -> log.info("Customer with id={} is found.", id),
                () -> log.warn("Customer with id={} is not found.", id)
        );

        return maybeCustomer.map(customerReadMapper::map);
    }

    @Transactional(readOnly = true)
    public Optional<PersonReadDto> findBy(LocalDate birthDate, String name, String surname) {
        Optional<Person> maybePerson = personRepository.findBy(birthDate, name, surname);

        return maybePerson.map(personReadMapper::map);
    }

    public CustomerReadDto create(CustomerCreateEditDto customerCreateEditDto) {
        return Optional.of(customerCreateEditDto)
                .map(customerCreateEditMapper::map)
                .map(personRepository::save)
                .map(customerRepository::save)
                .map(customer -> {
                    log.info("New customer with id={} is saved.", customer.getIdPerson());
                    return customerReadMapper.map(customer);
                })
                .orElseThrow();
    }

    public Optional<CustomerReadDto> update(Integer id, CustomerCreateEditDto customerDto) {
        return customerRepository.findById(id)
                .map(customer -> customerCreateEditMapper.map(customerDto, customer))
                .map(personRepository::save)
                .map(customerRepository::saveAndFlush)
                .map(customerReadMapper::map);
    }

    public boolean delete(Integer id) {
        return customerRepository.findById(id)
                .map(customer -> {
                    personRepository.delete(customer);
                    personRepository.flush();
                    return true;
                })
                .orElse(false);
    }

//    public void attachPersonalInfo(int idCustomer, String phoneNumber, String email) {
//        Optional<Customer> maybeCustomer = customerRepository.findById(idCustomer);
//
//        maybeCustomer.ifPresentOrElse(customer -> {
//                    PersonalInformation personalInformation = PersonalInformation.builder()
//                            .customer(customer)
//                            .phoneNumber(phoneNumber)
//                            .email(email)
//                            .build();
//
//                    customer.setPersonalInformation(personalInformation);
//                    customerRepository.save(customer);
//                    log.info("Personal information with id={} is attached.", personalInformation.getIdCustomer());
//                },
//
//                () -> log.warn("Customer with id={} does not exist.", idCustomer));
//    }

//    public void attachAddress(int idCustomer, int houseNumber, String street) {
//        Optional<PersonalInformation> maybePersonalInformation = personalInformationRepository.findById(idCustomer);
//
//        maybePersonalInformation.ifPresentOrElse(personalInformation -> {
//                    Address address = Address.builder()
//                            .personalInformation(personalInformation)
//                            .houseNumber(houseNumber)
//                            .street(street)
//                            .build();
//
//                    addressRepository.save(address);
//                    log.info("Address with id={} is attached to customer with id={}.", address.getId(), personalInformation.getIdCustomer());
//                },
//
//                () -> log.warn("Customer with id={} has not attached personal information.", idCustomer));
//    }

    @Transactional(readOnly = true)
    public Integer getCustomerId(LocalDate birthDate, String name, String surname) {
        Optional<Person> maybePerson = personRepository.findBy(birthDate, name, surname);

        return maybePerson.map(person -> {
                    log.info("Person with id={} is found.", person.getIdPerson());
                    return person.getIdPerson();
                })
                .orElseGet(() -> {
                    log.warn("person with birthDate={}, name={}, surname={} is not found.", birthDate, name, surname);
                    return -1;
                });
    }
}
