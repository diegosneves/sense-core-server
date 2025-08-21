package br.com.diegosneves.resources;

import br.com.diegosneves.responses.MensagemCustom;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

import static org.eclipse.microprofile.openapi.annotations.enums.SchemaType.ARRAY;

@Path("/teste")
@Tag(name = "Teste", description = "Endpoint para testes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ExampleResource {

    @GET
    @Operation(summary = "Message endpoint", description = "Returns custom message")
    @APIResponse(
        responseCode = "200",
        description = "Custom message",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(
                    type = ARRAY,
                    implementation = MensagemCustom.class
            )
        )
    )
    public Response hello() {
        String message = "Olá Mundo!!!";
        MensagemCustom entity = MensagemCustom.of(message);
        return Response.ok(List.of(entity)).build();
    }
}

