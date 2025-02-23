package com.michael_delivery.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorResponse errorResponse = new ErrorResponse(
                OffsetDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                errors,
                request.getDescription(false).replace("uri=","")
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ErrorResponse errorResponse = new ErrorResponse(
                OffsetDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getLocalizedMessage(),
                errors,
                request.getDescription(false).replace("uri=","")
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> handleDuplicateVehicleType(SQLIntegrityConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ErrorResponse errorResponse = new ErrorResponse(
                OffsetDateTime.now(),
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                errors,
                ""
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}

class ErrorResponse {
    private OffsetDateTime timestamp;
    private int status;
    private String error;
    private Map<String, String> details;
    private String path;

    public ErrorResponse(OffsetDateTime timestamp, int status, String error, Map<String, String> details, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.details = details;
        this.path = path;
    }

    // Getters
    public OffsetDateTime getTimestamp() { return timestamp; }
    public int getStatus() { return status; }
    public String getError() { return error; }
    public Map<String, String> getDetails() { return details; }
    public String getPath() { return path; }
}
