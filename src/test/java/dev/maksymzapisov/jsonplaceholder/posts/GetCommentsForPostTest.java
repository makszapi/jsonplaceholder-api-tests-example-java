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

import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;

public class GetCommentsForPostTest extends BaseApiTest {

    private final PostsClient postClient = new PostsClient();
    private static final Post existingPost = TestDataProvider.getPostFromExistingUser();

     @Tag("Functional")
     @Tag("Contract")
     @Test
     @DisplayName("Get comments for post by valid id")
     public void getCommentsForPostByIdTest() {
         Response response = postClient.getCommentsForPost(existingPost.getId());

         response.then()
                     .spec(DefaultJsonSpecs.defaultJsonResponseSpec())
                     .body(matchesJsonSchemaInClasspath("schemas/commentArray.json"));

         List<Comment> comments = ResponseExtractors.asList(response, Comment.class);

         assertThat(comments)
                 .isNotEmpty()
                 .hasSizeGreaterThan(1)
                 .allMatch(comment -> comment.getPostId() == existingPost.getId());
     }

     @Tag("Functional")
     @Test
     @DisplayName("Get comments for post by invalid id")
     public void getCommentsForPostByInvalidIdTest() {
         Response response = postClient.getCommentsForPost("invalidId");

         response.then()
                    .spec(DefaultJsonSpecs.defaultJsonResponseSpec());

         List<Comment> comments = ResponseExtractors.asList(response, Comment.class);

         assertThat(comments).isEmpty();
     }
}
