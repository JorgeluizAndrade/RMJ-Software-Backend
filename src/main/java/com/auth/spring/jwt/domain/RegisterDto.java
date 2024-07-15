package com.auth.spring.jwt.domain;

public record RegisterDto(String username, String password, UserRole role) {
}
