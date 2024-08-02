package com.shvaiale.irishpub.mapper;

import com.shvaiale.irishpub.database.entity.Customer;
import com.shvaiale.irishpub.dto.CustomerCreateEditDto;
import org.springframework.stereotype.Component;

@Component
public class CustomerCreateEditMapper implements Mapper<CustomerCreateEditDto, Customer> {

    @Override
    public Customer map(CustomerCreateEditDto from) {
        return Customer.builder()
                .birthDate(from.birthDate())
                .name(from.name())
                .surname(from.surname())
                .build();
    }

    @Override
    public Customer map(CustomerCreateEditDto from, Customer to) {
        to.setBirthDate(from.birthDate());
        to.setName(from.name());
        to.setSurname(from.surname());
        return to;
    }
}
