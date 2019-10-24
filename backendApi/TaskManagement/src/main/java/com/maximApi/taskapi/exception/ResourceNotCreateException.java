package com.maximApi.taskapi.exception;

public class ResourceNotCreateException extends RuntimeException{

	String resourceName;

	public ResourceNotCreateException(String resourceName) {
		super(String.format("%s is not created" , resourceName));
		this.resourceName = resourceName;
	}

	public String getResourceName() {
		return resourceName;
	}

	
	
}
