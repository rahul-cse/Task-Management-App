package com.maximApi.taskapi.advice;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.maximApi.taskapi.exception.ResourceNotFoundException;
import com.maximApi.taskapi.model.payload.ApiResponse;
import com.maximApi.taskapi.util.Utility;

@RestControllerAdvice
public class TaskManagementControllerAdvice{
	
	
	 private final MessageSource messageSource;

	    @Autowired
	    public TaskManagementControllerAdvice(MessageSource messageSource) {
	        this.messageSource = messageSource;

	    }

	/*
	 * @ExceptionHandler(StudentNotFoundException) public final
	 * ResponseEntity<ErrorDetails>
	 * handleUserNotFoundException(StudentNotFoundException ex, WebRequest request)
	 * { ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
	 * request.getDescription(false)); return new ResponseEntity<>(errorDetails,
	 * HttpStatus.NOT_FOUND); }
	 */
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ApiResponse processValidationError(MethodArgumentNotValidException ex,WebRequest request) {

		/*
		 * Map<String, Object> body = new LinkedHashMap<>(); body.put("timestamp", new
		 * Date()); body.put("status", status.value());
		 * 
		 * //Get all errors List<String> errors = ex.getBindingResult()
		 * .getFieldErrors() .stream() .map(x -> x.getDefaultMessage())
		 * .collect(Collectors.toList());
		 * 
		 * body.put("errors", errors);
		 * 
		 * return new ResponseEntity<>(body, headers, status);
		 */
			
			
			BindingResult result = ex.getBindingResult();
	        List<ObjectError> allErrors = result.getAllErrors();
	        String data = processAllErrors(allErrors).stream().collect(Collectors.joining("\n"));
	        return new ApiResponse(false, data, ex.getClass().getName(), Utility.resolvePathFromWebRequest(request));
	}
	
	/**
     * Utility Method to generate localized message for a list of field errors
     *
     * @param allErrors the field errors
     * @return the list
     */
    private List<String> processAllErrors(List<ObjectError> allErrors) {
        return allErrors.stream().map(this::resolveLocalizedErrorMessage).collect(Collectors.toList());
    }
    
    
    
    /**
     * Resolve localized error message. Utility method to generate a localized error
     * message
     *
     * @param objectError the field error
     * @return the string
     */
    private String resolveLocalizedErrorMessage(ObjectError objectError) {
        Locale currentLocale = LocaleContextHolder.getLocale();
        String localizedErrorMessage = messageSource.getMessage(objectError, currentLocale);
        //logger.info(localizedErrorMessage);
        return localizedErrorMessage;
    }
    
    
    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiResponse handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        return new ApiResponse(false, ex.getMessage(), ex.getClass().getName(), Utility.resolvePathFromWebRequest(request));
    }
    
    
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResponse exceptionHandlerForInvalidPathVariableWithAnnotation(ConstraintViolationException ex, WebRequest request) {
        return new ApiResponse(false, ex.getMessage(), ex.getClass().getName(), Utility.resolvePathFromWebRequest(request));
    }
	
}
