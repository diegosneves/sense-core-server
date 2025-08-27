package br.com.diegosneves.resources.user;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
class UserResourceTest {

    @Test
    void testCreateUserEndpointWithoutBody() {
        given()
                .header("Content-Type", "application/json")
                .when().post("/api/users")
                .then()
                .statusCode(400);
    }


}