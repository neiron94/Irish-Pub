package com.shvaiale.irishpub.database.repository;

import com.shvaiale.irishpub.database.entity.PersonalInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalInformationRepository extends JpaRepository<PersonalInformation, Integer> {
}
