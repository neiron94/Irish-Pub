package com.shvaiale.irishpub.dto;

import java.time.LocalDate;

public record CustomerFilter(String name, String surname, LocalDate birthDate) {
}
