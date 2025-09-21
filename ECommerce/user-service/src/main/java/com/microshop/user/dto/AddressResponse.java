package com.microshop.user.dto;

public record AddressResponse(
        Long id,
        String street,
        String city,
        String state,
        String country,
        String zip,
        String type
) {
}
