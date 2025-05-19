package com.akash.medistock.exception;

import lombok.Getter;

@Getter
public class InvalidRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String message;

	public InvalidRequestException(String message) {
		super();
		this.message = message;
	}

}
