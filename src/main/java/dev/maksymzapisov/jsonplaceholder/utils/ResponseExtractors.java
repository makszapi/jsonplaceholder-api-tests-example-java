package dev.maksymzapisov.jsonplaceholder.utils;

import io.restassured.response.Response;

import java.util.List;

public final class ResponseExtractors {

    private ResponseExtractors() {}

    public static <T> List<T> asList(Response response, Class<T> type) {
        return response.then().extract().jsonPath().getList("", type);
    }

    public static <T> T asObject(Response response, Class<T> type) {
        return response.then().extract().as(type);
    }
}
