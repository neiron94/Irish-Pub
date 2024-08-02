package com.shvaiale.irishpub.mapper;

import com.shvaiale.irishpub.database.entity.Customer;
import com.shvaiale.irishpub.dto.CustomerReadDto;
import com.shvaiale.irishpub.dto.PersonalInformationReadCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomerReadMapper implements Mapper<Customer, CustomerReadDto> {

    private final PersonalInformationReadMapper personalInformationReadMapper;

    @Override
    public CustomerReadDto map(Customer from) {
        PersonalInformationReadCreateDto personalInfo = Optional.ofNullable(from.getPersonalInformation())
                .map(personalInformationReadMapper::map)
                .orElse(null);

        return new CustomerReadDto(
                from.getIdPerson(),
                from.getName(),
                from.getSurname(),
                from.getBirthDate(),
                from.getDiscountCardNumber(),
                personalInfo);
    }
}
