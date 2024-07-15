package com.auth.spring.jwt.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth.spring.jwt.domain.User;
import com.auth.spring.jwt.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTFilter extends OncePerRequestFilter {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private TokenService tokenService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		var token = this.recoverToken(request);

		if (token != null) {
			var username = tokenService.validateToken(token);
			if (username != null) {
				User user = userRepo.findByUsername(username);
				if (user != null) {
					var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(authentication);
				} else {
					// Log that the user was not found
					System.out.println("User not found with username: " + username);
				}
			} else {
				// Log that the token is invalid or expired
				System.out.println("Token is invalid or expired");
			}
		}
		filterChain.doFilter(request, response);
	}

	private String recoverToken(HttpServletRequest request) {
		var authHeader = request.getHeader("Authorization");
		if (authHeader == null)
			return null;
		return authHeader.replace("Bearer ", "");
	}
}
