package com.maximApi.taskapi.util;

import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

public class Utility {
	
	  public static String resolvePathFromWebRequest(WebRequest request) {
	        try {
	            return ((ServletWebRequest) request).getRequest().getAttribute("javax.servlet.forward.request_uri").toString();
	        } catch (Exception ex) {
	            return null;
	        }
	    }

}
