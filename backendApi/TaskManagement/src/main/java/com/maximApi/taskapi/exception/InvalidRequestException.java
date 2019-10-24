package com.maximApi.taskapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRequestException extends RuntimeException{

	private final String resourceName;

	public InvalidRequestException(String resourceName) {
		super(String.format("Invalid Request for %s", resourceName));
		this.resourceName = resourceName;
	}

	public String getResourceName() {
		return resourceName;
	}
	
	
}
