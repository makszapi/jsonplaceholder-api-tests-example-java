package dev.maksymzapisov.jsonplaceholder.specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;

public final class DefaultJsonSpecs {

    private DefaultJsonSpecs() {}

    public static RequestSpecification defaultJsonRequestSpec() {

        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification defaultJsonResponseSpec() {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(HttpStatus.SC_OK)
                .build();
    }

}
