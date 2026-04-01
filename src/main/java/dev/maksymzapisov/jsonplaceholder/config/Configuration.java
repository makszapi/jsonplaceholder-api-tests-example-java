package dev.maksymzapisov.jsonplaceholder.config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadType;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.Sources;

@LoadPolicy(LoadType.MERGE)
@Sources({"system:properties", "classpath:api.properties"})

public interface Configuration extends Config {

    @Key("api.base.uri")
    String baseURI();

    @Key("api.base.path")
    String basePath();

    @Key("api.port")
    int port();

    @Key("log.all")
    boolean logAll();
}
