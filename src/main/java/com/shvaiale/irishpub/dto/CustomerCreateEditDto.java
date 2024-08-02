package com.shvaiale.irishpub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CustomerCreateEditDto(@Past
                                    @NotNull
                                    LocalDate birthDate,

                                    @NotBlank
                                    @Size(min = 3, max = 40)
                                    String name,

                                    @NotBlank
                                    @Size(min = 3, max = 40)
                                    String surname,

                                    PersonalInformationReadCreateDto personalInformation) {
}
