package dev.maksymzapisov.jsonplaceholder.specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public final class PostsSpecs {

    private PostsSpecs() {}

    public static RequestSpecification postsRequestSpec() {
        return new RequestSpecBuilder()
                .addRequestSpecification(DefaultJsonSpecs.defaultJsonRequestSpec())
                .setBasePath("/posts")
                .build();
    }
}
