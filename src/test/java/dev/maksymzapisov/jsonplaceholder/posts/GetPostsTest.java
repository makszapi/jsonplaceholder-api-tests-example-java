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

import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.collection.IsMapWithSize.anEmptyMap;

public class GetPostsTest  extends BaseApiTest {

    private final PostsClient postClient = new PostsClient();
    private static final Post existingPost = TestDataProvider.getPostFromExistingUser();

    @Tag("Functional")
    @Tag("Contract")
    @Test
    @DisplayName("Get all posts")
    public void getAllPostsTest() {

        Response response = postClient.getAllPosts();

        response.then()
                    .spec(DefaultJsonSpecs.defaultJsonResponseSpec())
                    .body(matchesJsonSchemaInClasspath("schemas/postArray.json"));

        List<Post> posts = ResponseExtractors.asList(response, Post.class);

        assertThat(posts)
                .isNotEmpty()
                .hasSizeGreaterThan(1)
                .contains(existingPost);
    }

     @Tag("Functional")
     @Tag("Contract")
     @Test
     @DisplayName("Get post by valid id")
     public void getPostByIdTest() {
         Response response = postClient.getPostById(existingPost.getId());

         response.then()
                    .spec(DefaultJsonSpecs.defaultJsonResponseSpec())
                    .body(matchesJsonSchemaInClasspath("schemas/post.json"));

         Post post = ResponseExtractors.asObject(response, Post.class);

         assertThat(post).isEqualTo(existingPost);
    }

    @Tag("Functional")
    @Test
    @DisplayName("Get post by invalid id")
    public void getPostByInvalidIdTest() {
        postClient.getPostById("invalidId")
                .then()
                    .spec(DefaultJsonSpecs.notFoundJsonResponseSpec())
                    .body("", anEmptyMap());
    }
}
