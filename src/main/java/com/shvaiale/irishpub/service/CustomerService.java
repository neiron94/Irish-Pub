package com.shvaiale.irishpub.service;

import com.shvaiale.irishpub.database.entity.Address;
import com.shvaiale.irishpub.database.entity.Customer;
import com.shvaiale.irishpub.database.entity.Person;
import com.shvaiale.irishpub.database.entity.PersonalInformation;
import com.shvaiale.irishpub.database.repository.AddressRepository;
import com.shvaiale.irishpub.database.repository.CustomerRepository;
import com.shvaiale.irishpub.database.repository.PersonRepository;
import com.shvaiale.irishpub.database.repository.PersonalInformationRepository;
import com.shvaiale.irishpub.dto.CustomerDto;
import com.shvaiale.irishpub.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PersonRepository personRepository;
    private final PersonalInformationRepository personalInformationRepository;
    private final AddressRepository addressRepository;
    private final CustomerMapper customerMapper;

    @Transactional(readOnly = true)
    public Optional<CustomerDto> findById(Integer id) {
        Optional<Customer> maybeCustomer = customerRepository.findById(id);

        maybeCustomer.ifPresentOrElse(
                customer -> log.info("Customer with id={} is found.", id),
                () -> log.warn("Customer with id={} is not found.", id)
        );

        return maybeCustomer.map(customerMapper::map);
    }

    public CustomerDto addNewCustomer(LocalDate birthDate, String name, String surname, Long discountCardNumber) {
        Customer customer = Customer.builder()
                .birthDate(birthDate)
                .name(name)
                .surname(surname)
                .discountCardNumber(discountCardNumber)
                .build();

        personRepository.save(customer);
        customerRepository.save(customer);

        log.info("New customer with id={} is saved.", customer.getIdPerson());
        return customerMapper.map(customer);
    }

    public void attachPersonalInfo(int idCustomer, String phoneNumber, String email) {
        Optional<Customer> maybeCustomer = customerRepository.findById(idCustomer);

        maybeCustomer.ifPresentOrElse(customer -> {
                    PersonalInformation personalInformation = PersonalInformation.builder()
                            .customer(customer)
                            .phoneNumber(phoneNumber)
                            .email(email)
                            .build();

                    customer.setPersonalInformation(personalInformation);
                    customerRepository.save(customer);
                    log.info("Personal information with id={} is attached.", personalInformation.getIdCustomer());
                },

                () -> log.warn("Customer with id={} does not exist.", idCustomer));
    }

    public void attachAddress(int idCustomer, int houseNumber, String street) {
        Optional<PersonalInformation> maybePersonalInformation = personalInformationRepository.findById(idCustomer);

        maybePersonalInformation.ifPresentOrElse(personalInformation -> {
                    Address address = Address.builder()
                            .personalInformation(personalInformation)
                            .houseNumber(houseNumber)
                            .street(street)
                            .build();

                    addressRepository.save(address);
                    log.info("Address with id={} is attached to customer with id={}.", address.getId(), personalInformation.getIdCustomer());
                },

                () -> log.warn("Customer with id={} has not attached personal information.", idCustomer));
    }

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
