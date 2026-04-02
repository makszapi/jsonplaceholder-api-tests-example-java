package dev.maksymzapisov.jsonplaceholder.users;

import dev.maksymzapisov.jsonplaceholder.BaseApiTest;
import dev.maksymzapisov.jsonplaceholder.clients.UsersClient;
import dev.maksymzapisov.jsonplaceholder.data.TestDataProvider;
import dev.maksymzapisov.jsonplaceholder.model.User;
import dev.maksymzapisov.jsonplaceholder.specs.DefaultJsonSpecs;
import dev.maksymzapisov.jsonplaceholder.utils.ResponseExtractors;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.collection.IsMapWithSize.anEmptyMap;


public class GetUsersTest extends BaseApiTest {

    private final UsersClient userClient = new UsersClient();
    private static final User existingUser = TestDataProvider.getExistingUser();

    @Tag("Functional")
    @Tag("Contract")
    @Test
    @DisplayName("Get all users")
    public void getAllUsersTest() {
        Response response = userClient.getAllUsers();

        response.then()
                    .spec(DefaultJsonSpecs.defaultJsonResponseSpec())
                    .body(matchesJsonSchemaInClasspath("schemas/userArray.json"));

        List<User> users = ResponseExtractors.asList(response, User.class);

        assertThat(users)
                .isNotEmpty()
                .hasSizeGreaterThan(1)
                .contains(existingUser);
    }

    @Tag("Functional")
    @Tag("Contract")
    @Test
    @DisplayName("Get user by valid id")
    public void getUserByIdTest() {
        Response response = userClient.getUserById(existingUser.getId());

        response.then()
                    .spec(DefaultJsonSpecs.defaultJsonResponseSpec())
                    .body(matchesJsonSchemaInClasspath("schemas/user.json"));

        User user = ResponseExtractors.asObject(response, User.class);

        assertThat(user).isEqualTo(existingUser);
    }

    @Tag("Functional")
    @Test
    @DisplayName("Get user by invalid id")
    public void getUserByInvalidIdTest() {
        userClient.getUserById("invalidId")
                .then()
                    .spec(DefaultJsonSpecs.notFoundJsonResponseSpec())
                    .body("", anEmptyMap());
    }
}
