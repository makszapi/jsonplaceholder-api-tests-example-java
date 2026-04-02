package dev.maksymzapisov.jsonplaceholder.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.maksymzapisov.jsonplaceholder.model.Comment;
import dev.maksymzapisov.jsonplaceholder.model.Post;
import dev.maksymzapisov.jsonplaceholder.model.User;

import java.io.IOException;

public final class TestDataProvider {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private TestDataProvider() {
    }

    public static User getExistingUser() {
        return fromJson(TestDataConstants.EXISTING_USER_JSON, User.class);
    }

    public static Post getPostFromExistingUser() {
        return fromJson(TestDataConstants.POST_FROM_EXISTING_USER_JSON, Post.class);
    }

    public static Comment getCommentFromPostsFromExistingUser() {
        return fromJson(TestDataConstants.COMMENT_FOR_POST_FROM_EXISTING_USER_JSON, Comment.class);
    }

    private static <T> T fromJson(String json, Class<T> type) {
        try {
            return MAPPER.readValue(json, type);
        } catch (IOException e) {
            throw new RuntimeException("Failed to deserialize test data into " + type.getSimpleName(), e);
        }
    }
}
