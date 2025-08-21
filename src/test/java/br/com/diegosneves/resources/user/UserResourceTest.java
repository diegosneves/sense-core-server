package br.com.diegosneves.resources.user;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class UserResourceTest {

    @Test
    void testCreateUserEndpoint() {
        String requestBody = """
            {
                "name": "João Silva",
                "email": "joao.silva@example.com",
                "phone": "+55 11 99999-9999",
                "profile": "ADMIN",
                "username": "joao.silva"
            }
            """;

        given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when().post("/api/users")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("name", is("João Silva"))
                .body("email", is("joao.silva@example.com"))
                .body("phone", is("+55 11 99999-9999"))
                .body("profile", is("ADMIN"))
                .body("username", is("joao.silva"))
                .body("permissions", is("Full Access"));
    }

    @Test
    void testCreateUserEndpointWithoutBody() {
        given()
                .header("Content-Type", "application/json")
                .when().post("/api/users")
                .then()
                .statusCode(400);
    }


}