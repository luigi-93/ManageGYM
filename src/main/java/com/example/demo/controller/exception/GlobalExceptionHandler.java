package com.example.demo.controller.exception;


import jakarta.validation.ConstraintDeclarationException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice

//gestisco un accezione, in questo caso quello che viene fuori dalla validazione
public class GlobalExceptionHandler {

    //la validazione che viene lanciata è l'orgmento del metodo
    //<Map<String, String>> qui gli dico di inserire gli errori che tornano, campo e tipo di errore
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> validationException(ConstraintViolationException e){
        Map<String, String> errors = new HashMap<>();
        e.getConstraintViolations().forEach((error) -> {
            String field = error.getPropertyPath().toString();
            String defaultMessage = error.getMessageTemplate();
            errors.put(field, defaultMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
