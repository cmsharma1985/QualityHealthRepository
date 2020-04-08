package com.sharecare.qualityhealth.exception;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(RequiredException.class)
	public ResponseEntity<Object> requiredException(RequiredException ex, WebRequest request) {
		String errors = ex.getMessage();
		if (StringUtils.isBlank(errors)) {
			errors = "Something on the request is missing.";
		}
		return new ResponseEntity<Object>(new HttpStatusCodeErrorMessage(errors), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
		String errors = ex.getMessage();
		if (StringUtils.isBlank(errors)) {
			errors = "Resource not found.";
		}
		return new ResponseEntity<Object>(new HttpStatusCodeErrorMessage(errors), HttpStatus.BAD_REQUEST);
	}

	@Data
	@Getter
	@Setter
	@AllArgsConstructor
	class HttpStatusCodeErrorMessage {
		private String message;
	}
}
