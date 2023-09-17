package com.aspire.loan.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidLoanStatusException extends RuntimeException {

    public InvalidLoanStatusException(String message) {
        super(message);
    }

    public InvalidLoanStatusException(Throwable e) {
        super(e);
    }

    public InvalidLoanStatusException(String s, DataIntegrityViolationException ex) {
        super(s, ex);
    }
}

