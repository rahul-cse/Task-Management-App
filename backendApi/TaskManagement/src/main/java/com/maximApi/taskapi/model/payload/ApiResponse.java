package com.maximApi.taskapi.model.payload;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
	


	    private final Object data;
	    private final Boolean success;
	    private final String timestamp;
	    private final String cause;
	    private final String path;

	    public ApiResponse(Boolean success, Object data, String cause, String path) {
	        this.timestamp = Instant.now().toString();
	        this.data = data;
	        this.success = success;
	        this.cause = cause;
	        this.path = path;
	    }

	    public ApiResponse(Boolean success, Object data) {
	        this.timestamp = Instant.now().toString();
	        this.data = data;
	        this.success = success;
	        this.cause = null;
	        this.path = null;
	    }

	    public Object getData() {
	        return data;
	    }

	    public Boolean getSuccess() {
	        return success;
	    }

	    public String getTimestamp() {
	        return timestamp;
	    }

	    public String getCause() {
	        return cause;
	    }

	    public String getPath() {
	        return path;
	    }
	
}
