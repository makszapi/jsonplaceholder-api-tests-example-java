package dev.maksymzapisov.jsonplaceholder;
import dev.maksymzapisov.jsonplaceholder.config.Configuration;
import dev.maksymzapisov.jsonplaceholder.config.ConfigurationProvider;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseApiTest {

    protected static Configuration configuration;

    @BeforeAll
    public static void beforeAllTests() {
        configuration = ConfigurationProvider.getConfiguration();
        resolveLoggingStrategy();
    }

    private static void resolveLoggingStrategy() {
        if (configuration.logAll()) {
            RestAssured.replaceFiltersWith(new RequestLoggingFilter(), new ResponseLoggingFilter());
        } else {
            RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        }
    }
}
