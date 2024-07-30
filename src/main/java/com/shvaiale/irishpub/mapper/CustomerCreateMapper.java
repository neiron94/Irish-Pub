package com.shvaiale.irishpub.mapper;

import com.shvaiale.irishpub.database.entity.Customer;
import com.shvaiale.irishpub.dto.CustomerCreateDto;
import org.springframework.stereotype.Component;

@Component
public class CustomerCreateMapper implements Mapper<CustomerCreateDto, Customer> {
    @Override
    public Customer map(CustomerCreateDto from) {
        return Customer.builder()
                .birthDate(from.birthDate())
                .name(from.name())
                .surname(from.surname())
                .build();
    }
}
