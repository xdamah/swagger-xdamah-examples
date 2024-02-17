package com.example;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.github.xdamah.advice.SwaggerValidationExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AnotherExceptionHandler extends SwaggerValidationExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(AnotherExceptionHandler.class);

	/*
	 * @ExceptionHandler(InvalidRequestException.class) public
	 * ResponseEntity<String> handle(final InvalidRequestException
	 * invalidRequestException) {
	 * 
	 * ValidationReport validationReport =
	 * invalidRequestException.getValidationReport(); String json =
	 * "{\"problem\":true}"; HttpHeaders headers= new HttpHeaders();
	 * headers.setContentType(MediaType.APPLICATION_JSON); ResponseEntity<String>
	 * responseEntity = new ResponseEntity<>(json, headers,HttpStatus.BAD_REQUEST);
	 * // responseEntity.getHeaders().add(HttpHeaders.CONTENT_TYPE,
	 * MediaType.APPLICATION_JSON_VALUE); return responseEntity; }
	 * 
	 * @ExceptionHandler(MissingServletRequestParameterException.class) public
	 * ResponseEntity<Map<String, String>> handle(final
	 * MissingServletRequestParameterException e) { Map<String, String> map= new
	 * HashMap<>(); map.put(e.getParameterName(), e.getDetailMessageCode()); return
	 * new ResponseEntity<>(map, HttpStatus.BAD_REQUEST); }
	 */

	@ExceptionHandler(Exception.class)

	public ResponseEntity<Map<String, String>> handle(final Exception e) {
		Map<String, String> map = new HashMap<>();

		UUID uuid = UUID.randomUUID();
		String logRef = uuid.toString();
		String msg = "Unexpected Problem Happened. Note refID=" + logRef;

		logger.error(msg, e);

		map.put("problem", msg);
		return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
