package br.com.diegosneves.domain.validations.handlers;

import br.com.diegosneves.exceptions.ErrorData;

import java.util.ArrayList;
import java.util.List;

public class Notification implements ValidationHandler {

    private final List<ErrorData> errors;

    private Notification(final List<ErrorData> errors) {
        this.errors = errors;
    }

    public static Notification create() {
        return new Notification(new ArrayList<>());
    }

    public static Notification create(final ErrorData anError) {
        return (Notification) create().append(anError);
    }

    public static Notification create(final Throwable throwable) {
        return create(ErrorData.of(throwable.getMessage()));
    }


    @Override
    public ValidationHandler append(final ErrorData anError) {
        if (anError != null) {
            this.errors.add(anError);
        }
        return this;
    }

    @Override
    public ValidationHandler append(final String fieldName, final String defaultMessage) {
        if (fieldName != null && defaultMessage != null) {
            this.append(ErrorData.of(fieldName, defaultMessage));
        }
        return this;
    }

    @Override
    public ValidationHandler append(final ValidationHandler anotherHandler) {
        if (anotherHandler != null) {
            this.errors.addAll(anotherHandler.getErrors());
        }
        return this;
    }

    @Override
    public List<ErrorData> getErrors() {
        return this.errors;
    }

    @Override
    public boolean hasErrors() {
        return ValidationHandler.super.hasErrors();
    }

    @Override
    public ErrorData getFirstError() {
        return ValidationHandler.super.getFirstError();
    }
}
