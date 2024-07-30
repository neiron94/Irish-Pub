package com.shvaiale.irishpub.dto;

import java.time.LocalDate;

public record CustomerCreateDto(LocalDate birthDate, String name, String surname) {
}
