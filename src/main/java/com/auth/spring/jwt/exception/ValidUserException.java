package com.auth.spring.jwt.exception;

public class ValidUserException extends RuntimeException {
	public ValidUserException(String message) {
		super(message);
	}

	public ValidUserException(String mensagem, Object... params) {
		super(String.format(mensagem, params));
	}
}
