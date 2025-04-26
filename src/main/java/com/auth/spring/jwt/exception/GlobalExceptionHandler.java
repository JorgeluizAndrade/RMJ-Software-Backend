package com.auth.spring.jwt.exception;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Resource
	private MessageSource messageSource;

	private HttpHeaders headers() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

	private ResponseError responseError(String message, HttpStatus statusCode) {
		ResponseError responseError = new ResponseError();
		responseError.setStatus("error");
		responseError.setError(message);
		responseError.setStatusCode(statusCode.value());
		return responseError;
	}

	@ExceptionHandler({ UserAlreadyExistsException.class })
	public ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistsException exception) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
	}

	@ExceptionHandler({ ValidUserException.class })
	public ResponseEntity<Object> handleValidUerException(ValidUserException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
	}

	@ExceptionHandler({ RuntimeException.class })
	public ResponseEntity<Object> handleRuntimeException(RuntimeException exception) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
	}

}
