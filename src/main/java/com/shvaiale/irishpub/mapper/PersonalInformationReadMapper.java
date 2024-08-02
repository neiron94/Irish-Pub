package com.shvaiale.irishpub.mapper;

import com.shvaiale.irishpub.database.entity.PersonalInformation;
import com.shvaiale.irishpub.dto.PersonalInformationReadCreateDto;
import org.springframework.stereotype.Component;

@Component
public class PersonalInformationReadMapper implements Mapper<PersonalInformation, PersonalInformationReadCreateDto> {

    @Override
    public PersonalInformationReadCreateDto map(PersonalInformation from) {
        return from == null ? null : new PersonalInformationReadCreateDto(
                from.getIdCustomer(),
                from.getPhoneNumber(),
                from.getEmail(),
                from.getStreet(),
                from.getHouseNumber());
    }
}
