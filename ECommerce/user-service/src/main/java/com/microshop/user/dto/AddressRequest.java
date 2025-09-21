package com.microshop.user.dto;

import jakarta.validation.constraints.NotBlank;

public record AddressRequest(
        @NotBlank String street,
        @NotBlank String city,
        @NotBlank String state,
        @NotBlank String country,
        @NotBlank String zip,
        @NotBlank String type
) {
}
