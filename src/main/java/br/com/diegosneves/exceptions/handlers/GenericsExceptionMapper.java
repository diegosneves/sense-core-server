package br.com.diegosneves.exceptions.handlers;

import br.com.diegosneves.exceptions.ApiErrorResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GenericsExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        final var httpStatus = ResponseStatusHelper.of(Response.Status.BAD_REQUEST).httpStatus();
        return Response
                .status(httpStatus)
                .entity(ApiErrorResponse.of(
                        httpStatus,
                        exception.getMessage(),
                        exception)
                )
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

}
