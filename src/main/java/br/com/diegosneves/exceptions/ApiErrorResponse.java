package br.com.diegosneves.exceptions;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.Instant;
import java.util.List;

public record ApiErrorResponse(
        @Schema(examples = {"500"}) int status,
        String message,
        Instant timestamp,
        String debugMessage,
        List<ErrorData> errors
) {

    public static ApiErrorResponse of(final int status, final String message, final List<ErrorData> errors) {
        return new ApiErrorResponse(status, message, Instant.now(), null,errors);
    }

    public static ApiErrorResponse of(final int status, final String message, final Throwable throwable) {
        return new ApiErrorResponse(
                status,
                message,
                Instant.now(),
                throwable != null ? throwable.getLocalizedMessage() : null,
                null);
    }

}
