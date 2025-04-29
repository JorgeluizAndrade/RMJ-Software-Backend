package com.auth.spring.jwt.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth.spring.jwt.api.dto.RegisterDto;
import com.auth.spring.jwt.domain.User;
import com.auth.spring.jwt.exception.UserAlreadyExistsException;
import com.auth.spring.jwt.exception.ValidUserException;
import com.auth.spring.jwt.repository.UserRepository;

@Service
public class UserService {

	private static final String REGEX_EMAIL = "^(?!\\.)(?!.*\\.\\.)([A-Z0-9_'+-\\.]*)([A-Z0-9_'+-])@([A-Z0-9][A-Z0-9\\-]*\\.)+[A-Z]{2,}$";

	private final UserRepository userRepository;
	
	private final BCryptPasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public User registerUser(RegisterDto data) {

		if (this.userRepository.findByEmail(data.email()) != null) {
			throw new UserAlreadyExistsException("User email already exists.");
		}
		
		if (!isEmailValid(data.email())) {
			throw new ValidUserException("Email is not valid. Try with another email.");
		}

		String encryptedPassword = passwordEncoder.encode(data.password());
		User newUser = new User(data.username(), data.name(), data.email(), encryptedPassword, data.role());

		return this.userRepository.save(newUser);
	}

	public boolean isEmailValid(String email) {
		Pattern pattern = Pattern.compile(REGEX_EMAIL, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(email);

		return matcher.matches();
	}

}
