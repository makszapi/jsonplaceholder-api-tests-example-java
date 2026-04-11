package dev.maksymzapisov.jsonplaceholder.posts;

import dev.maksymzapisov.jsonplaceholder.BaseApiTest;
import dev.maksymzapisov.jsonplaceholder.clients.PostsClient;
import dev.maksymzapisov.jsonplaceholder.data.TestDataProvider;
import dev.maksymzapisov.jsonplaceholder.model.Post;
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

public class SearchPostsTest extends BaseApiTest {

    private final PostsClient postClient = new PostsClient();
    private static final Post existingPost = TestDataProvider.getPostFromExistingUser();

    private static final List<Arguments> searchPostsValidValueForQueryParamSet = List.of(
            argumentSet("userId", Map.of("userId", String.valueOf(existingPost.getUserId()))),
            argumentSet("title", Map.of("title", existingPost.getTitle())),
            argumentSet("body", Map.of("body", existingPost.getBody()))
    );

    private static final List<Arguments> searchPostsInvalidValuesForQueryParamsSet = List.of(
            argumentSet("one valid and one invalid values", Map.of("title", existingPost.getTitle(), "userId", "nonExistentUserId")),
            argumentSet("both invalid values", Map.of("body", "nonExistentBody", "id", "nonExistentPostId"))
    );

    @Tag("Functional")
    @Test
    @DisplayName("Search posts using unique id value for query parameters returns single post")
    public void searchPostsUsingUniqueIdValueForQueryParamTest() {
        Response response = postClient.getPostsByQueryParams(Map.of("id", String.valueOf(existingPost.getId())));

        response.then()
                    .spec(DefaultJsonSpecs.defaultJsonResponseSpec());

        List<Post> foundPosts = ResponseExtractors.asList(response, Post.class);

        assertThat(foundPosts)
                .isNotEmpty()
                .hasSize(1)
                .contains(existingPost);
    }

    @Tag("Functional")
    @DisplayName("Search posts using a valid value")
    @ParameterizedTest(name = "for {argumentSetName} query parameter")
    @FieldSource("searchPostsValidValueForQueryParamSet")
    public void searchPostsUsingValidValueForQueryParamTest(Map<String, String> params) {
        Response response = postClient.getPostsByQueryParams(params);

        response.then()
                    .spec(DefaultJsonSpecs.defaultJsonResponseSpec());

        List<Post> foundPosts = ResponseExtractors.asList(response, Post.class);

        assertThat(foundPosts)
                .isNotEmpty()
                .hasSizeGreaterThanOrEqualTo(1)
                .contains(existingPost);
    }

    @Tag("Functional")
    @Test
    @DisplayName("Search posts using multiple valid values for query parameters")
    public void searchPostsUsingMultipleValidValuesForQueryParamsTest() {

        Map<String, String> params = Map.of(
                "title", existingPost.getTitle(),
                "body", existingPost.getBody()
        );

        Response response = postClient.getPostsByQueryParams(params);

        response.then()
                    .spec(DefaultJsonSpecs.defaultJsonResponseSpec());

        List<Post> foundPosts = ResponseExtractors.asList(response, Post.class);

        assertThat(foundPosts)
                .isNotEmpty()
                .hasSizeGreaterThanOrEqualTo(1)
                .contains(existingPost);
    }

    @Tag("Functional")
    @Test
    @DisplayName("Search posts using an invalid value for the query parameter returns an empty list")
    public void searchPostsUsingInvalidValueForQueryParamTest() {
        Response response = postClient.getPostsByQueryParams(Map.of("title", "nonExistentTitle"));

        response.then()
                    .spec(DefaultJsonSpecs.defaultJsonResponseSpec());

        List<Post> foundPosts = ResponseExtractors.asList(response, Post.class);

        assertThat(foundPosts).isEmpty();
    }

    @Tag("Functional")
    @DisplayName("Search posts returns an empty list")
    @ParameterizedTest(name = "when {argumentSetName} are used for query parameters")
    @FieldSource("searchPostsInvalidValuesForQueryParamsSet")
    public void searchPostsUsingMultipleInvalidValuesForQueryParamsTest(Map<String, String> params) {
        Response response = postClient.getPostsByQueryParams(params);

        response.then()
                    .spec(DefaultJsonSpecs.defaultJsonResponseSpec());

        List<Post> foundPosts = ResponseExtractors.asList(response, Post.class);

        assertThat(foundPosts).isEmpty();
    }
}
