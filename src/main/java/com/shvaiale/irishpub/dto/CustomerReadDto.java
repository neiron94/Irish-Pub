package com.shvaiale.irishpub.dto;

import java.time.LocalDate;

public record CustomerReadDto(Integer id, String name, String surname, LocalDate birthDate, Long discountCardNumber, PersonalInformationReadCreateDto personalInformation) {
}
