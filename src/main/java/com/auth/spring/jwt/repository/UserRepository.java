package com.auth.spring.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.auth.spring.jwt.domain.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	 User findByUsername(String username);

}
