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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.FieldSource;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.argumentSet;

public class SearchUsersTest extends BaseApiTest {

    private final UsersClient userClient = new UsersClient();
    private static final User existingUser = TestDataProvider.getExistingUser();

    private static final List<Arguments> searchUserValidNonUniqueValueForQueryParamSet = List.of(
            argumentSet("name", Map.of("name", existingUser.getName())),
            argumentSet("website",Map.of("website", existingUser.getWebsite())),
            argumentSet("phone", Map.of("phone", existingUser.getPhone()))
    );

    private static final List<Arguments> searchUserValidUniqueValueForQueryParamSet = List.of(
            argumentSet("id", Map.of("id", String.valueOf(existingUser.getId()))),
            argumentSet("username", Map.of("username", existingUser.getUsername())),
            argumentSet("email", Map.of("email", existingUser.getEmail()))
    );

    private static final List<Arguments> searchUserInvalidValuesForQueryParamsSet = List.of(
            argumentSet("one valid and one invalid values", Map.of("email", existingUser.getEmail(), "name", "nonExistentName")),
            argumentSet("both invalid values",  Map.of("email", "nonExistentEmail", "name", "nonExistentName"))
    );

    @Tag("Functional")
    @DisplayName("Search user using a valid unique value")
    @ParameterizedTest(name = "for {argumentSetName} query parameter")
    @FieldSource("searchUserValidUniqueValueForQueryParamSet")
    public void searchUserUsingValidUniqueValueForQueryParamTest(Map<String, String> params) {
        Response response = userClient.getUsersByQueryParams(params);

        response.then()
                    .spec(DefaultJsonSpecs.defaultJsonResponseSpec());

        List<User> foundUsers = ResponseExtractors.asList(response, User.class);

        assertThat(foundUsers)
                .isNotEmpty()
                .hasSize(1)
                .first()
                .isEqualTo(existingUser);
    }

    @Tag("Functional")
    @DisplayName("Search user using a valid not unique value")
    @ParameterizedTest(name = "for {argumentSetName} query parameter")
    @FieldSource("searchUserValidNonUniqueValueForQueryParamSet")
    public void searchUserUsingValidNonUniqueValueForQueryParamTest(Map<String, String> params) {
        Response response = userClient.getUsersByQueryParams(params);

        response.then()
                    .spec(DefaultJsonSpecs.defaultJsonResponseSpec());

        List<User> foundUsers = ResponseExtractors.asList(response, User.class);

        assertThat(foundUsers)
                .isNotEmpty()
                .hasSizeGreaterThanOrEqualTo(1)
                .contains(existingUser);
    }

    @Tag("Functional")
    @Test
    @DisplayName("Search user using multiple valid values for query parameters")
    public void searchUserUsingMultipleValidValuesForQueryParamsTest() {
        Map<String, String> params = Map.of(
                "username", existingUser.getUsername(),
                "email", existingUser.getEmail()
        );

        Response response = userClient.getUsersByQueryParams(params);

        response.then()
                    .spec(DefaultJsonSpecs.defaultJsonResponseSpec());

        List<User> foundUsers = ResponseExtractors.asList(response, User.class);

        assertThat(foundUsers)
                .isNotEmpty()
                .hasSize(1)
                .first()
                .isEqualTo(existingUser);
    }

    @Tag("Functional")
    @Test
    @DisplayName("Search user using an invalid value for the query parameter returns an empty list")
    public void searchUserUsingInvalidValueForQueryParamTest() {
        Response response = userClient.getUsersByQueryParams(Map.of("username", "nonExistentUser"));

        response.then()
                    .spec(DefaultJsonSpecs.defaultJsonResponseSpec());

        List<User> foundUsers = ResponseExtractors.asList(response, User.class);

        assertThat(foundUsers).isEmpty();
    }

    @Tag("Functional")
    @DisplayName("Search user returns an empty list")
    @ParameterizedTest(name = "when {argumentSetName} are used for query parameters")
    @FieldSource("searchUserInvalidValuesForQueryParamsSet")
    public void searchUserUsingMultipleInvalidValuesForQueryParamsTest(Map<String, String> params) {
        Response response = userClient.getUsersByQueryParams(params);

        response.then()
                    .spec(DefaultJsonSpecs.defaultJsonResponseSpec());

        List<User> foundUsers = ResponseExtractors.asList(response, User.class);

        assertThat(foundUsers).isEmpty();
    }
}
