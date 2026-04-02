package dev.maksymzapisov.jsonplaceholder.specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public final class UsersSpecs {

    private UsersSpecs() {}

    public static RequestSpecification usersRequestSpec() {
        return new RequestSpecBuilder()
                .addRequestSpecification(DefaultJsonSpecs.defaultJsonRequestSpec())
                .setBasePath("/users")
                .build();
    }
}
