package dev.maksymzapisov.jsonplaceholder.specs;

import dev.maksymzapisov.jsonplaceholder.config.Configuration;
import dev.maksymzapisov.jsonplaceholder.config.ConfigurationProvider;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public final class ApiClientContext {

    private ApiClientContext() {}

    public static RequestSpecification apply() {
        Configuration configuration = ConfigurationProvider.getConfiguration();

        return new RequestSpecBuilder().
                setBaseUri(configuration.baseURI()).
                setBasePath(configuration.basePath()).
                setPort(configuration.port()).
                build();
    }
}
