package br.com.diegosneves.domain.validations;

import br.com.diegosneves.domain.validations.handlers.ValidationHandler;
import br.com.diegosneves.exceptions.ErrorData;

public abstract class Validator {

    private final ValidationHandler handler;

    protected Validator(final ValidationHandler handler) {
        this.handler = handler;
    }

    public abstract void validate();

    public ValidationHandler getHandler() {
        return this.handler;
    }

    protected void validateStringFieldIsNullOrBlank(final String field, final String fieldValue) {
        if (this.isNullOrBlank(fieldValue)) {
            this.handler.append(ErrorData.of(field, String.format("Field '%s' cannot be empty or null", field)));
        }
    }

    protected boolean isNullOrBlank(final String field) {
        return field == null || field.isBlank();
    }


}
