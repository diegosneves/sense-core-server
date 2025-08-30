package br.com.diegosneves.exceptions.handlers;

import br.com.diegosneves.exceptions.ApiErrorResponse;
import br.com.diegosneves.exceptions.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NoFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException exception) {
        final var httpStatus = ResponseStatusHelper.of(Response.Status.NOT_FOUND).httpStatus();
        return Response
                .status(httpStatus)
                .entity(ApiErrorResponse.of(
                        httpStatus,
                        exception.getMessage(),
                        exception.getErrors())
                )
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

}
