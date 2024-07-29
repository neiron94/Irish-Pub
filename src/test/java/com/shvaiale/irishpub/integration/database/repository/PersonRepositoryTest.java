package com.shvaiale.irishpub.integration.database.repository;

import com.shvaiale.irishpub.database.entity.Person;
import com.shvaiale.irishpub.database.repository.PersonRepository;
import com.shvaiale.irishpub.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class PersonRepositoryTest extends IntegrationTestBase {

    private final LocalDate BIRTH_DATE = LocalDate.of(1995, 4, 22);
    private final String WRONG_NAME = "Wrong";
    private final String NAME = "Michael";
    private final String SURNAME = "O'Connor";

    private final PersonRepository personRepository;

    @Test
    void findByBirthDateAndNameAndSurname_Success() {
        Optional<Person> maybePerson = personRepository.findBy(BIRTH_DATE, NAME, SURNAME);

        assertThat(maybePerson).isNotEmpty();
    }

    @Test
    void findByBirthDateAndNameAndSurname_NotFound() {
        Optional<Person> maybePerson = personRepository.findBy(BIRTH_DATE, WRONG_NAME, SURNAME);

        assertThat(maybePerson).isEmpty();
    }
}