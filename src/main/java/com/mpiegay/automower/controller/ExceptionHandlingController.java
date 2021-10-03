package com.mpiegay.automower.controller;

import com.mpiegay.automower.exception.InvalidInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandlingController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlingController.class);


    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<String> handleError(HttpServletRequest req, InvalidInputException ex) {
        LOGGER.error("Request: " + req.getRequestURL() + " raised " + ex);
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
