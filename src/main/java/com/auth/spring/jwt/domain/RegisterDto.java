package com.auth.spring.jwt.domain;

public record RegisterDto(String username,String name, String email, String password, UserRole role) {
}
