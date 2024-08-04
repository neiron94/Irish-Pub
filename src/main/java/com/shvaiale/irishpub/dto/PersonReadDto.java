package com.shvaiale.irishpub.dto;

import java.time.LocalDate;

public record PersonReadDto(Integer id, String name, String surname, LocalDate birthDate) {
}
