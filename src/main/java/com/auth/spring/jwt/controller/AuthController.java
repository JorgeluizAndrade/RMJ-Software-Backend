package com.auth.spring.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

@RestController
@RequestMapping("auth")
public class AuthController {

	@Autowired 
	private TokenService tokenService;
	
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepo;

	@PostMapping("/login")
	public ResponseEntity login(@RequestBody @Validated AuthenticationDto data) {
		try {
			var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
			var auth = authenticationManager.authenticate(usernamePassword);
			
			var token = tokenService.generateToken((User) auth.getPrincipal());
			
			return ResponseEntity.ok(new LoginResponseDto(token));
		} catch (AuthenticationException e) {
			return ResponseEntity.status(403).body("Forbidden: " + e.getMessage());
		}
	}

	@PostMapping("/register")
	public ResponseEntity register(@RequestBody @Validated RegisterDto data) {
		if (this.userRepo.findByUsername(data.username()) != null)
			return ResponseEntity.badRequest().build();

		String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
		User newUser = new User(data.username(), encryptedPassword, data.role());

		this.userRepo.save(newUser);

		return ResponseEntity.ok().build();
	}
}
