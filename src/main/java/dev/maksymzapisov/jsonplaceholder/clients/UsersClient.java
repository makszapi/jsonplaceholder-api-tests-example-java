package dev.maksymzapisov.jsonplaceholder.clients;

import dev.maksymzapisov.jsonplaceholder.specs.UsersSpecs;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;


public class UsersClient {

    private static final RequestSpecification USERS_REQUEST_SPEC = UsersSpecs.usersRequestSpec();

    public Response getAllUsers() {
        return given()
                    .spec(USERS_REQUEST_SPEC)
                .when()
                    .get();
    }

    public Response getUserById(String userId) {
        return given()
                    .spec(USERS_REQUEST_SPEC)
                    .pathParam("id", userId)
                .when()
                    .get("/{id}");
    }

    public Response getUserById(int id) {
        return this.getUserById(String.valueOf(id));
    }

    public Response getUsersByQueryParams(Map<String, String> queryParams) {
        return given()
                    .spec(USERS_REQUEST_SPEC)
                    .queryParams(queryParams)
                .when()
                    .get();
    }
}
