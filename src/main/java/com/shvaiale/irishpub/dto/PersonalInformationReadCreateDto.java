package com.shvaiale.irishpub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PersonalInformationReadCreateDto(

        @NotNull
        @Min(1)
        Integer id,

        @NotBlank
        String phoneNumber,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String street,

        @NotNull
        @Min(1)
        Integer houseNumber) {
}
