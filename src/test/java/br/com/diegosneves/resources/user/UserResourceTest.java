package br.com.diegosneves.resources.user;

import br.com.diegosneves.enums.UserProfile;
import br.com.diegosneves.requests.user.UserCreateRequest;
import br.com.diegosneves.util.MySQLTestResource;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
@QuarkusTestResource(MySQLTestResource.class)  // Adiciona o Testcontainer
@DisplayName("User Resource Integration Tests")
class UserResourceTest {


	public static final String API_USERS_PATH = "/api/users";

	@Test
	@DisplayName("Should return 400 when creating user without body")
	void givenMissingRequestBody_whenCreatingUser_thenShouldReturnBadRequest() {
		given()
			.header("Content-Type", "application/json")
			.when().post(API_USERS_PATH)
			.then()
			.statusCode(400);
	}

	@Test
	@DisplayName("Should create user successfully with valid request")
	@Transactional
	void givenValidUserRequest_whenCreatingUser_thenShouldReturnCreatedUserSuccessfully() {
		// Arrange
		final var request = UserCreateRequest.of(
			"John Doe",
			"john@example.com",
			"+5551996406958",
			UserProfile.ADMIN,
			"johndoe"
		);

		// Act & Assert
		given()
			.header("Content-Type", "application/json")
			.body(request)
			.when().post(API_USERS_PATH)
			.then()
			.statusCode(200)
			.body("id", notNullValue())
			.body("name", equalTo("John Doe"))
			.body("email", equalTo("john@example.com"))
			.body("username", equalTo("johndoe"))
			.body("phone", equalTo("+5551996406958"))
			.body("profile", equalTo("ADMIN"))
			.body("enabled", equalTo(true));

	}

	@Test
	@DisplayName("Should return 422 when creating user with empty name")
	@Transactional
	void givenEmptyName_whenCreatingUser_thenShouldReturnValidationError() {
		final var request = UserCreateRequest.of(
			"",  // Nome vazio
			"john@example.com",
			"+5551996406958",
			UserProfile.ADMIN,
			"johndoe"
		);

		given()
			.contentType(ContentType.JSON)
			.body(request)
			.when()
			.post(API_USERS_PATH)
			.then()
			.statusCode(422);
	}

	@Test
	@DisplayName("Should return 422 when creating user with null profile")
	@Transactional
	void givenNullProfile_whenCreatingUser_thenShouldReturnValidationError() {
		final var request = UserCreateRequest.of(
			"John Doe",
			"john@example.com",
			"+5551996406958",
			null,  // Profile null
			"johndoe"
		);

		given()
			.contentType(ContentType.JSON)
			.body(request)
			.when()
			.post(API_USERS_PATH)
			.then()
			.statusCode(422)
			.body("errors[0].field", equalTo("Profile"));
	}

	@Test
	@DisplayName("Should return 404 when fetching non-existent user")
	void givenNonExistentUserId_whenFetchingUser_thenShouldReturnNotFoundError() {
		given()
			.contentType(ContentType.JSON)
			.when()
			.get("/api/users/999999")
			.then()
			.statusCode(404)
			.body("errors[0].message", equalTo("User not found"));
	}

	@Test
	@DisplayName("Should fetch user successfully by ID")
	@Transactional
	void givenValidUserId_whenFetchingUser_thenShouldReturnUserSuccessfully() {
		// Primeiro, cria um usuário
		final var createRequest = UserCreateRequest.of(
			"Jane Doe",
			"jane@example.com",
			"+5551996406959",
			UserProfile.ADMIN,
			"janedoe"
		);

		final var userId = createUserAndRetrieveId(createRequest);

		// Depois, busca o usuário criado
		given()
			.contentType(ContentType.JSON)
			.when()
			.get("/api/users/" + userId)
			.then()
			.statusCode(200)
			.body("id", equalTo(userId))
			.body("name", equalTo("Jane Doe"))
			.body("email", equalTo("jane@example.com"))
			.body("username", equalTo("janedoe"));
	}

	@Test
	@DisplayName("Should return 422 when creating user with empty or null data")
	@Transactional
	void givenEmptyAndNullFields_whenCreatingUser_thenShouldReturnMultipleValidationErrors() {
		final var request = UserCreateRequest.of(
			"",
			null,
			"",
			null,
			"   "
		);

		given()
			.contentType(ContentType.JSON)
			.body(request)
			.when()
			.post(API_USERS_PATH)
			.then()
			.statusCode(422)
			.body("errors", hasSize(5));
	}

	@Test
	@DisplayName("Should fetch all users successfully")
	@Transactional
	void givenPaginatedEndpoint_whenFetchingAllUsers_thenShouldReturnPagedResults() {
		final var createRequest = UserCreateRequest.of(
			"Jane Doe",
			"jane@example.com",
			"+5551996406959",
			UserProfile.ADMIN,
			"janedoe"
		);

		final var createRequest2 = UserCreateRequest.of(
			"Paulo Doe",
			"paulo@example.com",
			"+5551996406960",
			UserProfile.ADMIN,
			"paulodoe"
		);

		createUserAndRetrieveId(createRequest);
		createUserAndRetrieveId(createRequest2);

		// Depois, busca o usuário criado
		given()
			.contentType(ContentType.JSON)
			.when()
			.get("/api/users/all?page=0&perPage=1")
			.then()
			.statusCode(200)
			.body("currentPage", equalTo(0))
			.body("perPage", equalTo(1))
			.body("items", hasSize(1))
			.body("items[0].name", equalTo("Jane Doe"));
	}

	private static String createUserAndRetrieveId(UserCreateRequest createRequest) {
		return given()
			.contentType(ContentType.JSON)
			.body(createRequest)
			.when()
			.post(API_USERS_PATH)
			.then()
			.statusCode(200)
			.extract()
			.body()
			.jsonPath()
			.getString("id");
	}

}
