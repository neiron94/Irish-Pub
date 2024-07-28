package com.shvaiale.irishpub.database.repository;

import com.shvaiale.irishpub.database.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Integer> {

    @Query("select p from Person p where p.birthDate = :birthDate and p.name = :name and p.surname = :surname")
    Optional<Person> findBy(LocalDate birthDate, String name, String surname);
}
