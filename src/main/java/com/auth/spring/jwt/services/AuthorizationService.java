package com.auth.spring.jwt.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.auth.spring.jwt.repository.UserRepository;

@Service
public class AuthorizationService implements UserDetailsService{

	private final UserRepository userRepository;
		
	public AuthorizationService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return userRepository.findByEmail(email);
	}
	
}
