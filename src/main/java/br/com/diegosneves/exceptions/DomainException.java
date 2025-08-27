package br.com.diegosneves.exceptions;

import java.util.List;

public class DomainException extends RuntimeException {

    public static final String DEFAULT_ERROR_MESSAGE = "Validation errors found";

    protected final List<ErrorData> errors;

    protected DomainException(final String message, final List<ErrorData> errors) {
        super(message);
        this.errors = errors;
    }

    public static DomainException with(final List<ErrorData> errors) {
        return new DomainException(DEFAULT_ERROR_MESSAGE, errors);
    }

    public static DomainException with(final ErrorData anError) {
        return new DomainException(DEFAULT_ERROR_MESSAGE, anError.toList());
    }

    public List<ErrorData> getErrors() {
        return this.errors;
    }

}
