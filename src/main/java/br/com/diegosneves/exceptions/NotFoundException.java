package br.com.diegosneves.exceptions;

import java.util.List;

public class NotFoundException extends DomainException {

    public static final String DEFAULT_ERROR_MESSAGE = "Resource not found";

    protected NotFoundException(final String message, final List<ErrorData> errors) {
        super(message, errors);
    }

    public static NotFoundException with(final List<ErrorData> errors) {
        return new NotFoundException(DEFAULT_ERROR_MESSAGE, errors);
    }

    public static NotFoundException with(final ErrorData anError) {
        return new NotFoundException(DEFAULT_ERROR_MESSAGE, anError.toList());
    }

}
