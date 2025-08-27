package br.com.diegosneves.domain.validations.handlers;

import br.com.diegosneves.exceptions.ErrorData;

import java.util.List;

public interface ValidationHandler {

    ValidationHandler append(ErrorData anError);
    ValidationHandler append(String fieldName, String defaultMessage);
    ValidationHandler append(ValidationHandler anotherHandler);

    List<ErrorData> getErrors();

    default boolean hasErrors() {
        return this.getErrors() != null && !this.getErrors().isEmpty();
    }

    default ErrorData getFirstError() {
        return this.hasErrors() ? this.getErrors().getFirst() : null;
    }

}
