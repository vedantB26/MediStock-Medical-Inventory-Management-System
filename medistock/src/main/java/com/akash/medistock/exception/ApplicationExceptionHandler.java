package com.akash.medistock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.akash.medistock.response.ResponseStructure;

@RestControllerAdvice
public class ApplicationExceptionHandler {

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ResponseStructure<Object>> handleAccessDeniedException(AccessDeniedException e) {
		ResponseStructure<Object> responseStructure = new ResponseStructure<>();
		responseStructure.setMessage(e.getMessage());
		responseStructure.setHttpStatusCode(HttpStatus.UNAUTHORIZED.value());
		return new ResponseEntity<ResponseStructure<Object>>(responseStructure, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ResponseStructure<Object>> handleNotFoundException(NotFoundException e) {
		ResponseStructure<Object> responseStructure = new ResponseStructure<>();
		responseStructure.setMessage(e.getMessage());
		responseStructure.setHttpStatusCode(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ResponseStructure<Object>>(responseStructure, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidRequestException.class)
	public ResponseEntity<ResponseStructure<Object>> handleInvalidRequestException(InvalidRequestException e) {
		ResponseStructure<Object> responseStructure = new ResponseStructure<>();
		responseStructure.setMessage(e.getMessage());
		responseStructure.setHttpStatusCode(HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<ResponseStructure<Object>>(responseStructure, HttpStatus.BAD_REQUEST);
	}

}
