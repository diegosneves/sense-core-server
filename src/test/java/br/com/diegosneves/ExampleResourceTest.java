package br.com.diegosneves;

import br.com.diegosneves.responses.MensagemCustom;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class ExampleResourceTest {
    @Test
    void testHelloEndpoint() {
        String message = "Olá Mundo!!!";
        MensagemCustom entity = MensagemCustom.of(message);
        given()
                .when().get("/api/teste")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("[0].mensagem", is("Olá Mundo!!!"))
                .body("size()", is(1));
    }

}