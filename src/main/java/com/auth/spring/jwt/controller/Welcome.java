package com.auth.spring.jwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Welcome {
	@GetMapping
	public String welcome() {
		return "Welcome to My Spring Boot Web API";
	}

	@GetMapping("/users")
	public String users() {
		return "Authorized user";
	}

	@GetMapping("/admin")
	public String managers() {
		return "Authorized manager";
	}
}