package com.shvaiale.irishpub.mapper;

import com.shvaiale.irishpub.database.entity.Person;
import com.shvaiale.irishpub.dto.PersonReadDto;
import org.springframework.stereotype.Component;

@Component
public class PersonReadMapper implements Mapper<Person, PersonReadDto> {
    @Override
    public PersonReadDto map(Person from) {
        return new PersonReadDto(
                from.getIdPerson(),
                from.getName(),
                from.getSurname(),
                from.getBirthDate());
    }
}
