package com.microshop.user.dto;

import com.microshop.user.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegisterRequest(


        @NotBlank @Size String name,
        @NotBlank @Size String username,
        @Email @NotBlank String email,
        @NotBlank @Size(min = 6)
        String password,
        String phone,
        Boolean active,
        UserRole role
) {
}
