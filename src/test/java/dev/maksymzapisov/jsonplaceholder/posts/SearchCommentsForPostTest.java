package dev.maksymzapisov.jsonplaceholder.posts;

import dev.maksymzapisov.jsonplaceholder.BaseApiTest;
import dev.maksymzapisov.jsonplaceholder.clients.PostsClient;
import dev.maksymzapisov.jsonplaceholder.data.TestDataProvider;
import dev.maksymzapisov.jsonplaceholder.model.Comment;
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

public class SearchCommentsForPostTest extends BaseApiTest {

    private final PostsClient postClient = new PostsClient();
    private static final Comment existingComment = TestDataProvider.getCommentFromPostsFromExistingUser();
    private static final Post existingPost = TestDataProvider.getPostFromExistingUser();

    private static final List<Arguments> searchCommentsValidValueForQueryParamSet = List.of(
            argumentSet("postId", Map.of("postId", String.valueOf(existingComment.getPostId()))),
            argumentSet("name", Map.of("name", existingComment.getName())),
            argumentSet("email", Map.of("email", existingComment.getEmail())),
            argumentSet("body", Map.of("body", existingComment.getBody()))
    );

    private static final List<Arguments> searchCommentsInvalidValuesForQueryParamsSet = List.of(
            argumentSet("one valid and one invalid values", Map.of("email", existingComment.getEmail(), "name", "nonExistentName")),
            argumentSet("both invalid values", Map.of("postId", "nonExistentPostId", "body", "nonExistentBody"))
    );

    @Tag("Functional")
    @Test
    @DisplayName("Search comments for post using unique id value for query parameters returns single comment")
    public void searchCommentsUsingUniqueIdValueForQueryParamTest() {
        Response response = postClient.getCommentsForPostByQueryParam(
                    existingPost.getId(),
                    Map.of("id", String.valueOf(existingComment.getId()))
                );

        response.then()
                    .spec(DefaultJsonSpecs.defaultJsonResponseSpec());

        List<Comment> foundComments = ResponseExtractors.asList(response, Comment.class);

        assertThat(foundComments)
                .isNotEmpty()
                .allMatch(comment -> comment.getPostId() == existingPost.getId())
                .hasSize(1)
                .first()
                .isEqualTo(existingComment);
    }

    @Tag("Functional")
    @DisplayName("Search comments for posts using a valid value")
    @ParameterizedTest(name = "for {argumentSetName} query parameter")
    @FieldSource("searchCommentsValidValueForQueryParamSet")
    public void searchCommentsUsingValidValueForQueryParamTest(Map<String, String> params) {
        Response response = postClient.getCommentsForPostByQueryParam(existingPost.getId(), params);

        response.then()
                    .spec(DefaultJsonSpecs.defaultJsonResponseSpec());

        List<Comment> foundComments = ResponseExtractors.asList(response, Comment.class);

        assertThat(foundComments)
                .isNotEmpty()
                .allMatch(comment -> comment.getPostId() == existingPost.getId())
                .hasSizeGreaterThanOrEqualTo(1)
                .contains(existingComment);
    }

    @Tag("Functional")
    @Test
    @DisplayName("Search comments for post using multiple valid values for query parameters")
    public void searchCommentsUsingMultipleValidValuesForQueryParamsTest() {

        Map<String, String> params = Map.of(
                "email", existingComment.getEmail(),
                "name", existingComment.getName()
        );

        Response response = postClient.getCommentsForPostByQueryParam(existingPost.getId(), params);

        response.then()
                    .spec(DefaultJsonSpecs.defaultJsonResponseSpec());

        List<Comment> foundComments = ResponseExtractors.asList(response, Comment.class);

        assertThat(foundComments)
                .isNotEmpty()
                .allMatch(comment -> comment.getPostId() == existingPost.getId())
                .hasSizeGreaterThanOrEqualTo(1)
                .contains(existingComment);
    }

    @Tag("Functional")
    @Test
    @DisplayName("Search comments for post using an invalid value for the query parameter returns an empty list")
    public void searchCommentsUsingInvalidValueForQueryParamTest() {
        Response response = postClient.getCommentsForPostByQueryParam(
                    existingPost.getId(),
                    Map.of("body", "nonExistentBody")
                );

        response.then()
                    .spec(DefaultJsonSpecs.defaultJsonResponseSpec());

        List<Comment> foundComments = ResponseExtractors.asList(response, Comment.class);

        assertThat(foundComments).isEmpty();
    }

    @Tag("Functional")
    @DisplayName("Search comments for post returns an empty list")
    @ParameterizedTest(name = "when {argumentSetName} are used for query parameters")
    @FieldSource("searchCommentsInvalidValuesForQueryParamsSet")
    public void searchCommentsUsingMultipleInvalidValuesForQueryParamsTest(Map<String, String> params) {
        Response response = postClient.getCommentsForPostByQueryParam(existingPost.getId(), params);

        response.then()
                    .spec(DefaultJsonSpecs.defaultJsonResponseSpec());

        List<Comment> foundComments = ResponseExtractors.asList(response, Comment.class);

        assertThat(foundComments).isEmpty();
    }
}
