package com.akash.medistock.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseStructure<T> {

	private String message;
	private int httpStatusCode;
	private T data;

}
