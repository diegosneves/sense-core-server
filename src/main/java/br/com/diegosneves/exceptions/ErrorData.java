package br.com.diegosneves.exceptions;

import java.util.List;

public class ErrorData {

    private final String field;
    private final String message;

    private ErrorData(final String field, final String message) {
        this.field = field;
        this.message = message;
    }

    public static ErrorData of(final String field, final String message) {
        return new ErrorData(field, message);
    }

    public static ErrorData of(final String message) {
        return new ErrorData(null, message);
    }

    public List<ErrorData> toList() {
        return List.of(this);
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
