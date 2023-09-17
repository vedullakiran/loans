package com.aspire.loan.controllers;

import com.aspire.loan.exceptions.*;
import com.aspire.loan.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidLoanStatusException.class)
    public ResponseEntity<ErrorResponse> handleCustomShortUrlConflictException(InvalidLoanStatusException ex) {
        ErrorResponse errorResponse = new ErrorResponse().setErrorMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleShortUrlNotFoundException(EntityNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse().setErrorMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(ValidateRequestException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidateRequestException ex) {
        ErrorResponse errorResponse = new ErrorResponse().setErrorMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(NotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(NotSupportedException ex) {
        ErrorResponse errorResponse = new ErrorResponse().setErrorMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(errorResponse);
    }

    @ExceptionHandler( UserAccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleForbiddenException(UserAccessDeniedException ex) {
        ErrorResponse errorResponse = new ErrorResponse().setErrorMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

}

