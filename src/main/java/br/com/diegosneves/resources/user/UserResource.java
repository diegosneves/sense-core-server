package br.com.diegosneves.resources.user;

import br.com.diegosneves.enums.UserProfile;
import br.com.diegosneves.exceptions.ApiErrorResponse;
import br.com.diegosneves.requests.user.UserCreateRequest;
import br.com.diegosneves.responses.user.UserCreatedResponse;
import br.com.diegosneves.services.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/users")
@Tag(name = "User", description = "User management API")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserService userService;

    @Inject
    public UserResource(final UserService userService) {
        this.userService = userService;
    }

    @POST
    @Operation(summary = "Create user", description = "Creates a new user")
    @APIResponse(
            responseCode = "200", description = "User created successfully",
            content = @Content(schema = @Schema(implementation = UserCreatedResponse.class))
    )
    @APIResponse(
            responseCode = "400", description = "Invalid request body",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
    )
    @APIResponse(
            responseCode = "422", description = "User already exists",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
    )
    public Response createUser(final UserCreateRequest request) {
        if (request == null) {
            return Response.status(400).build();
        }
        final var aNewUser = this.userService.createUser(request);
        return Response.ok(UserCreatedResponse.of(aNewUser)).build();
    }

    @GET
    @Path("/{userId}")
    @Operation(summary = "Fetch user", description = "Fetches a user by its ID")
    @APIResponse(
            responseCode = "200", description = "User fetched successfully",
            content = @Content(schema = @Schema(implementation = UserCreatedResponse.class))
    )
    @APIResponse(
            responseCode = "404", description = "User not found",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
    )
    public Response fetchUser(@PathParam("userId") final String userId) {
        final var response = this.userService.fetchUser(userId);
        return Response.ok(UserCreatedResponse.of(response)).build();
    }

}
