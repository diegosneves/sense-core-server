package br.com.diegosneves.exceptions.handlers;

import br.com.diegosneves.exceptions.ApiErrorResponse;
import br.com.diegosneves.exceptions.UserConstraintsException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class UserConstraintsExceptionMapper implements ExceptionMapper<UserConstraintsException> {

    @Override
    public Response toResponse(UserConstraintsException exception) {
        final var httpStatus = ResponseStatusHelper.of(422).httpStatus();
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
