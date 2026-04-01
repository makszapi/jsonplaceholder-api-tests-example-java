package dev.maksymzapisov.jsonplaceholder;
import dev.maksymzapisov.jsonplaceholder.config.Configuration;
import dev.maksymzapisov.jsonplaceholder.config.ConfigurationProvider;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.port;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseApiTest {

    protected static Configuration configuration;

    @BeforeAll
    public static void beforeAllTests() {
        configuration = ConfigurationProvider.getConfiguration();

        baseURI = configuration.baseURI();
        basePath = configuration.basePath();
        port = configuration.port();

        RestAssured.useRelaxedHTTPSValidation();

        resolveLoggingStrategy();
    }

    private static void resolveLoggingStrategy() {
        if (configuration.logAll()) {
            RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        } else {
            RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        }
    }
}
