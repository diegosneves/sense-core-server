package br.com.diegosneves.exceptions;

import java.util.List;

public class UserConstraintsException extends DomainException {

    public static final String DEFAULT_ERROR_MESSAGE = "User constraints violated. Please check the provided data.";

    protected UserConstraintsException(final String message, final List<ErrorData> errors) {
        super(message, errors);
    }

    public static UserConstraintsException with(final List<ErrorData> errors) {
        return new UserConstraintsException(DEFAULT_ERROR_MESSAGE, errors);
    }

    public static UserConstraintsException with(final ErrorData anError) {
        return new UserConstraintsException(DEFAULT_ERROR_MESSAGE, anError.toList());
    }

}
