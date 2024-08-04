package com.shvaiale.irishpub.mapper;

import com.shvaiale.irishpub.database.entity.Customer;
import com.shvaiale.irishpub.database.entity.PersonalInformation;
import com.shvaiale.irishpub.dto.CustomerCreateEditDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomerCreateEditMapper implements Mapper<CustomerCreateEditDto, Customer> {

    private final PersonalInformationCreateMapper personalInformationCreateMapper;

    @Override
    public Customer map(CustomerCreateEditDto from) {
        PersonalInformation personalInformation = Optional.ofNullable(from.personalInformation())
                .map(personalInformationCreateMapper::map)
                .orElse(null);

        return Customer.builder()
                .birthDate(from.birthDate())
                .name(from.name())
                .surname(from.surname())
                .personalInformation(personalInformation)
                .build();
    }

    @Override
    public Customer map(CustomerCreateEditDto from, Customer to) {
        PersonalInformation personalInformation = Optional.ofNullable(from.personalInformation())
                .map(personalInformationCreateMapper::map)
                .orElse(null);

        to.setBirthDate(from.birthDate());
        to.setName(from.name());
        to.setSurname(from.surname());
        to.setPersonalInformation(personalInformation);
        return to;
    }
}
