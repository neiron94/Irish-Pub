package com.shvaiale.irishpub.mapper;

import com.shvaiale.irishpub.database.entity.Customer;
import com.shvaiale.irishpub.dto.CustomerReadDto;
import org.springframework.stereotype.Component;

@Component
public class CustomerReadMapper implements Mapper<Customer, CustomerReadDto> {

    @Override
    public CustomerReadDto map(Customer from) {
        return new CustomerReadDto(
                from.getIdPerson(),
                from.getName(),
                from.getSurname(),
                from.getBirthDate());
    }
}
