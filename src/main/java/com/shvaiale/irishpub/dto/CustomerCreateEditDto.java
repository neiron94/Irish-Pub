package com.shvaiale.irishpub.dto;

import java.time.LocalDate;

public record CustomerCreateEditDto(LocalDate birthDate, String name, String surname) {
}
