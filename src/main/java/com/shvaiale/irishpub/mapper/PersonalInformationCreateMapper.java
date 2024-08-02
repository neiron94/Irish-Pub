package com.shvaiale.irishpub.mapper;

import com.shvaiale.irishpub.database.entity.PersonalInformation;
import com.shvaiale.irishpub.dto.PersonalInformationReadCreateDto;
import org.springframework.stereotype.Component;

@Component
public class PersonalInformationCreateMapper implements Mapper<PersonalInformationReadCreateDto, PersonalInformation> {

    @Override
    public PersonalInformation map(PersonalInformationReadCreateDto from) {
        return PersonalInformation.builder()
                .idCustomer(from.id())
                .email(from.email())
                .phoneNumber(from.phoneNumber())
                .street(from.street())
                .houseNumber(from.houseNumber())
                .build();
    }

    @Override
    public PersonalInformation map(PersonalInformationReadCreateDto from, PersonalInformation to) {
        to.setEmail(from.email());
        to.setPhoneNumber(from.phoneNumber());
        to.setStreet(from.street());
        to.setHouseNumber(from.houseNumber());
        return to;
    }
}
