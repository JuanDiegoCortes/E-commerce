package com.Ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(com.Ecommerce.exception.RecursoNoEncontradoException.class)
    public ResponseEntity<String> handleRecursoNoEncontrado(RecursoNoEncontradoException ex) {
        String mensaje = ex.getMessage();
        return new ResponseEntity<>(mensaje, HttpStatus.NOT_FOUND);
    }
}

