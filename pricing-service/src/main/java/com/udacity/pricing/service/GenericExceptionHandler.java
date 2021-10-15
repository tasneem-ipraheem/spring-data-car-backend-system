package com.udacity.pricing.service;

import org.springframework.data.rest.webmvc.RepositoryRestExceptionHandler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(basePackageClasses = RepositoryRestExceptionHandler.class)
public class GenericExceptionHandler {

    @ExceptionHandler
    ResponseEntity handle(Exception e) {
        return new ResponseEntity("Id not found", new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
}


//@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Id not found")
//public class PriceException extends RuntimeException  {
//
//	public PriceException(String message) {
//		super(message);
//	}
//}
