package com.microshop.user.dto;

import com.microshop.user.model.Status;
import com.microshop.user.model.UserRole;

public record UserResponse(
        Long id,
        String username,
        String email,
        UserRole role,
        Status status
) {
}
