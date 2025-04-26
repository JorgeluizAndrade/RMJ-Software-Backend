package com.auth.spring.jwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.spring.jwt.domain.AuthenticationDto;
import com.auth.spring.jwt.domain.LoginResponseDto;
import com.auth.spring.jwt.domain.RegisterDto;
import com.auth.spring.jwt.domain.User;
import com.auth.spring.jwt.repository.UserRepository;
import com.auth.spring.jwt.security.TokenService;
import com.auth.spring.jwt.services.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final TokenService tokenService;
	private final AuthenticationManager authenticationManager;
	private final UserService userService;

	public AuthController(TokenService tokenService, AuthenticationManager authenticationManager,
			UserRepository userRepository, UserService userService) {
		this.tokenService = tokenService;
		this.authenticationManager = authenticationManager;
		this.userService = userService;
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Validated AuthenticationDto data) {
		try {
			var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
			var auth = authenticationManager.authenticate(usernamePassword);

			var token = tokenService.generateToken((User) auth.getPrincipal());

			return ResponseEntity.ok(new LoginResponseDto(token));
		} catch (AuthenticationException e) {
			return ResponseEntity.status(403).body("Forbidden: " + e.getMessage());
		}
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody @Validated RegisterDto data) {
		User newUser = userService.registerUser(data);
		return ResponseEntity.ok().body(newUser);
	}
}
