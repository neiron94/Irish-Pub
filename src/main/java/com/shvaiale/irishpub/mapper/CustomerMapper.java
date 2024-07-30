package com.shvaiale.irishpub.mapper;

import com.shvaiale.irishpub.database.entity.Customer;
import com.shvaiale.irishpub.dto.CustomerDto;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper implements Mapper<Customer, CustomerDto> {

    @Override
    public CustomerDto map(Customer from) {
        return new CustomerDto(
                from.getIdPerson(),
                from.getName(),
                from.getSurname());
    }
}
