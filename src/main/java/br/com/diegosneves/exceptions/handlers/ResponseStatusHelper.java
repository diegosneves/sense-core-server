package br.com.diegosneves.exceptions.handlers;

import jakarta.ws.rs.core.Response;

public record ResponseStatusHelper(
        int httpStatus
) {

    public static ResponseStatusHelper of(final int statusCode) {
        return new ResponseStatusHelper(statusCode);
    }

    public static ResponseStatusHelper of(final Response.Status status) {
        return new ResponseStatusHelper(status.getStatusCode());
    }

}
