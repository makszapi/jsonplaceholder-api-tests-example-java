package dev.maksymzapisov.jsonplaceholder.e2e;

import dev.maksymzapisov.jsonplaceholder.BaseApiTest;
import dev.maksymzapisov.jsonplaceholder.clients.PostsClient;
import dev.maksymzapisov.jsonplaceholder.clients.UsersClient;
import dev.maksymzapisov.jsonplaceholder.data.TestDataProvider;
import dev.maksymzapisov.jsonplaceholder.model.Comment;
import dev.maksymzapisov.jsonplaceholder.model.Post;
import dev.maksymzapisov.jsonplaceholder.model.User;
import dev.maksymzapisov.jsonplaceholder.specs.DefaultJsonSpecs;
import dev.maksymzapisov.jsonplaceholder.utils.ResponseExtractors;
import org.apache.commons.validator.routines.EmailValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class SearchUserGetPostsAndValidateCommentsTest extends BaseApiTest {

    private final UsersClient userClient = new UsersClient();
    private final PostsClient postClient = new PostsClient();

    @Tag("E2E")
    @Test
    @DisplayName("Search user, get their posts and validate email in comments for each post")
    public void searchUserGetPostsAndValidateComments() {
        User existingUser = TestDataProvider.getExistingUser();

        Response usersResponse = userClient.getUsersByQueryParams(Map.of("username", existingUser.getUsername()));
        usersResponse.then()
                .spec(DefaultJsonSpecs.defaultJsonResponseSpec());
        List<User> foundUsers = ResponseExtractors.asList(usersResponse, User.class);
        assertThat(foundUsers)
                .isNotEmpty()
                .hasSize(1)
                .contains(existingUser);

        User foundUser = foundUsers.getFirst();
        Response postsResponse = postClient.getPostsByQueryParams(Map.of("userId", String.valueOf(foundUser.getId())));
        postsResponse.then()
                    .spec(DefaultJsonSpecs.defaultJsonResponseSpec());
        List<Post> foundUserPosts = ResponseExtractors.asList(postsResponse, Post.class);
        assertThat(foundUserPosts)
                .isNotEmpty()
                .hasSizeGreaterThanOrEqualTo(1)
                .allMatch(post -> post.getUserId() == foundUser.getId());

        foundUserPosts.forEach(post -> {
            Response commentsResponse = postClient.getCommentsForPost(post.getId());
            commentsResponse.then()
                        .spec(DefaultJsonSpecs.defaultJsonResponseSpec());
            List<Comment> foundCommentsForPost = ResponseExtractors.asList(commentsResponse, Comment.class);
            assertThat(foundCommentsForPost)
                    .isNotEmpty()
                    .hasSizeGreaterThanOrEqualTo(1)
                    .allMatch(comment -> comment.getPostId() == post.getId())
                    .allMatch(comment -> EmailValidator.getInstance().isValid(comment.getEmail()));
        });
    }
}
