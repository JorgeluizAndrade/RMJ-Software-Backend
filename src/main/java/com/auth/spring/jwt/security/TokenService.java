package com.auth.spring.jwt.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth.spring.jwt.domain.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

@Service
public class TokenService {
	@Value("${api.security.token.secret}")
	private String secret;

	public String generateToken(User user) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);

			String token = JWT.create().withIssuer("rmjsoftware").withSubject(user.getUsername())
					.withSubject(user.getEmail())
					.withSubject(user.getName())
					.withExpiresAt(genExpirationDate())
					.sign(algorithm);
			return token;
		} catch (JWTCreationException exception) {
			throw new RuntimeException("Error while generating token", exception);
		}
	}

	public String validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);

			return JWT.require(algorithm).withIssuer("rmjsoftware").build().verify(token).getSubject();
		} catch (JWTCreationException exception) {
			return "";
		}
	}

	private Instant genExpirationDate() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
}
