package dev.maksymzapisov.jsonplaceholder.config;

import org.aeonbits.owner.ConfigCache;

public final class ConfigurationProvider {

    private ConfigurationProvider() {}

    public static Configuration getConfiguration() {
        return ConfigCache.getOrCreate(Configuration.class);
    }
}
