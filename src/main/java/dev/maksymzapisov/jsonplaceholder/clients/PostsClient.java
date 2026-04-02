package dev.maksymzapisov.jsonplaceholder.clients;

import dev.maksymzapisov.jsonplaceholder.specs.PostsSpecs;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


import java.util.Map;

import static io.restassured.RestAssured.given;

public class PostsClient {

    private static final RequestSpecification POSTS_REQUEST_SPECIFICATION = PostsSpecs.postsRequestSpec();

    public Response getAllPosts() {
        return given()
                    .spec(POSTS_REQUEST_SPECIFICATION)
                .when()
                    .get();
    }

    public Response getPostsByQueryParams(Map<String, String> queryParams) {
        return given()
                    .spec(POSTS_REQUEST_SPECIFICATION)
                    .queryParams(queryParams)
                .when()
                    .get();
    }

    public Response getPostById(String postId) {
        return given()
                    .spec(POSTS_REQUEST_SPECIFICATION)
                    .pathParam("id", postId)
                .when()
                    .get("/{id}");
    }

    public Response getPostById(int postId) {
        return this.getPostById(String.valueOf(postId));
    }

    public Response getCommentsForPost(String postId) {
        return given()
                    .spec(POSTS_REQUEST_SPECIFICATION)
                    .pathParam("id", postId)
                .when()
                    .get("/{id}/comments");
    }

    public Response getCommentsForPost(int postId) {
        return this.getCommentsForPost(String.valueOf(postId));
    }

    public Response getCommentsForPostByQueryParam(String postId, Map<String, String> queryParams) {
        return given()
                    .spec(POSTS_REQUEST_SPECIFICATION)
                    .queryParams(queryParams)
                    .pathParam("id", postId)
                .when()
                    .get("/{id}/comments");
    }

    public Response getCommentsForPostByQueryParam(int postId, Map<String, String> queryParams) {
        return this.getCommentsForPostByQueryParam(String.valueOf(postId), queryParams);
    }
}