package com.auth.spring.jwt.api.dto;

import com.auth.spring.jwt.domain.UserRole;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record RegisterDto(@NotBlank(message = "Username cannot be blank") String username,
		@NotBlank(message = "Name cannot be blank") String name,
		@NotBlank(message = "Email cannot be blank") String email,

		@Min(value = 8, message = "Password should be greater than 8") @Max(value = 14, message = "Password should be less than 14") String password,

		UserRole role) 
{}
