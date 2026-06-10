package com.parabank.automation.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ConfigReader {

    private final Environment environment;

    @Autowired
    public ConfigReader(Environment environment) {
        this.environment = environment;
    }

    /**
     * Retrieve a property value as a String.
     *
     * @param key the property key (with or without the "parabank." prefix — both work)
     * @return the property value, or {@code null} if not found
     */
    public String get(String key) {
        String prefixed = ensurePrefix(key);
        return environment.getProperty(prefixed);
    }

    /**
     * Retrieve a property value as a String, with a default fallback.
     *
     * @param key          the property key
     * @param defaultValue returned if the key is not found
     * @return the property value, or {@code defaultValue}
     */
    public String get(String key, String defaultValue) {
        String prefixed = ensurePrefix(key);
        return environment.getProperty(prefixed, defaultValue);
    }

    /**
     * Retrieve a property converted to the given target type.
     *
     * @param key        the property key
     * @param targetType the type to convert to (e.g. {@code Integer.class})
     * @return the converted value, or {@code null} if not found
     */
    public <T> T get(String key, Class<T> targetType) {
        String prefixed = ensurePrefix(key);
        return environment.getProperty(prefixed, targetType);
    }

    /**
     * Retrieve a property converted to the given target type, with a default.
     *
     * @param key          the property key
     * @param targetType   the type to convert to
     * @param defaultValue returned if the key is not found
     * @return the converted value, or {@code defaultValue}
     */
    public <T> T get(String key, Class<T> targetType, T defaultValue) {
        String prefixed = ensurePrefix(key);
        return environment.getProperty(prefixed, targetType, defaultValue);
    }

    /**
     * Convenience — retrieve an {@code int} property.
     *
     * @param key the property key
     * @return the int value, or {@code 0} if not found
     */
    public int getInt(String key) {
        return get(key, Integer.class, 0);
    }

    /**
     * Convenience — retrieve a {@code boolean} property.
     *
     * @param key the property key
     * @return the boolean value, or {@code false} if not found
     */
    public boolean getBool(String key) {
        return get(key, Boolean.class, false);
    }

    /**
     * Ensures the key starts with "parabank." so callers can write
     * {@code config.get("credentials.username")} instead of
     * {@code config.get("parabank.credentials.username")}.
     */
    private String ensurePrefix(String key) {
        return key.startsWith("parabank.") ? key : "parabank." + key;
    }
}
