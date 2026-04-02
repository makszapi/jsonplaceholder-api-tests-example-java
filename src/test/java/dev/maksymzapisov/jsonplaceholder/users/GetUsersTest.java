package dev.maksymzapisov.jsonplaceholder.users;

import dev.maksymzapisov.jsonplaceholder.BaseApiTest;
import dev.maksymzapisov.jsonplaceholder.data.TestDataProvider;
import dev.maksymzapisov.jsonplaceholder.model.User;
import dev.maksymzapisov.jsonplaceholder.specs.DefaultJsonSpecs;
import dev.maksymzapisov.jsonplaceholder.specs.UsersSpecs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.collection.IsMapWithSize.anEmptyMap;


public class GetUsersTest extends BaseApiTest {

    private static final User existingUser = TestDataProvider.getExistingUser();

    @Tag("Functional")
    @Test
    @DisplayName("Get all users")
    public void getAllUsersTest() {
        List<User> users =
                given()
                    .spec(UsersSpecs.usersRequestSpec())
                .when()
                    .get()
                .then()
                    .spec(DefaultJsonSpecs.defaultJsonResponseSpec())
                    .body(matchesJsonSchemaInClasspath("schemas/userArray.json"))
                    .extract()
                    .jsonPath()
                    .getList("", User.class);

        assertThat(users)
                .isNotEmpty()
                .hasSizeGreaterThan(1)
                .usingRecursiveFieldByFieldElementComparator()
                .contains(existingUser);
    }

    @Tag("Functional")
    @Test
    @DisplayName("Get user by valid id")
    public void getUserByIdTest() {
        User user =
                given()
                    .spec(UsersSpecs.usersRequestSpec())
                    .pathParam("id", existingUser.getId())
                .when()
                    .get("/{id}")
                .then()
                    .spec(DefaultJsonSpecs.defaultJsonResponseSpec())
                    .body(matchesJsonSchemaInClasspath("schemas/user.json"))
                    .extract()
                    .as(User.class);

        assertThat(user)
                .usingRecursiveComparison()
                .isEqualTo(existingUser);
    }

    @Tag("Functional")
    @Test
    @DisplayName("Get user by invalid id")
    public void getUserByInvalidIdTest() {
        given()
                .spec(UsersSpecs.usersRequestSpec())
                .pathParam("id", "invalid")
        .when()
                .get("/{id}")
        .then()
                .spec(DefaultJsonSpecs.notFoundJsonResponseSpec())
                .body("", anEmptyMap());
    }
}
