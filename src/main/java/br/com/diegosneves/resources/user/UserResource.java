package br.com.diegosneves.resources.user;

import br.com.diegosneves.requests.user.UserCreateRequest;
import br.com.diegosneves.responses.user.UserResponse;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/users")
@Tag(name = "User", description = "User management API")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @POST
    @Operation(summary = "Create user", description = "Creates a new user")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "User created successfully",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))),
            @APIResponse(responseCode = "400", description = "Invalid request body")
    })
    public Response createUser(final UserCreateRequest request) {
        if (request == null) {
            return Response.status(400).build();
        }
        final var response = UserResponse.of(
                request.name(),
                request.email(),
                request.phone(),
                request.profile(),
                request.username()
        );
        return Response.ok(response).build();
    }

}
