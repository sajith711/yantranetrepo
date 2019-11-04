package org.yantranet.etas.exception;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.text.ParseException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler{
	private static final String ERROR = "errors";
	private static final String STATUS = "status";
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request){
		Map<String,Object> body = new LinkedHashMap<>();
		body.put(STATUS, status.value());
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).
				collect(Collectors.toList());
		body.put(ERROR, errors);
		return new ResponseEntity<Object>(body, headers, status);
	}
	
	@ExceptionHandler(PersistenceException.class)
	protected ResponseEntity<Object> handleJPAException(PersistenceException ex){
		Map<String,Object> body = new LinkedHashMap<>();
		body.put(STATUS, 500);
		List<String> errors = Arrays.asList("Exception occured in DB transaction: "+ex.getLocalizedMessage());
		body.put(ERROR, errors);
		return new ResponseEntity<Object>(body, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(NullPointerException.class)
	protected ResponseEntity<Object> handleNullPointerEception(NullPointerException ex){
		Map<String,Object> body = new LinkedHashMap<>();
		body.put(STATUS, 500);
		List<String> errors = Arrays.asList("Exception occured: "+ex.getLocalizedMessage());
		body.put(ERROR, errors);
		return new ResponseEntity<Object>(body, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ParseException.class)
	protected ResponseEntity<Object> handleParseException(ParseException ex) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put(STATUS, 500);
		List<String> errors = Arrays.asList("Exception Occured while parsing the date: "+ex.getMessage());
		ex.printStackTrace();
		body.put(ERROR, errors);
		return new ResponseEntity<>(body,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
