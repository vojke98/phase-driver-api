package io.github.vojke98.phase.driver.api;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Builds the JSON Schema (draft 2020-12) of one protocol's settings - what the API publishes under {@code /api/v1/protocols}, so a
 * client can build the form for a protocol it has never heard of, and tell a mistake from a setting before it saves. Unknown
 * settings are refused ({@code additionalProperties: false}), the same as when a device is saved.
 */
public final class SettingsSchema {

    private final Map<String, Object> properties = new LinkedHashMap<>();
    private final List<String> required = new ArrayList<>();

    public static SettingsSchema object() {
        return new SettingsSchema();
    }

    public SettingsSchema text(String name, String description, boolean mandatory) {
        properties.put(name, new LinkedHashMap<>(Map.of("type", "string", "minLength", 1, "description", description)));
        if (mandatory) required.add(name);
        return this;
    }

    /** Text with a default, used when it is left out. */
    public SettingsSchema text(String name, String description, String defaultValue) {
        Map<String, Object> property = new LinkedHashMap<>(Map.of("type", "string", "minLength", 1, "description", description));
        property.put("default", defaultValue);
        properties.put(name, property);
        return this;
    }

    /**
     * A secret (a password, an SNMP community): {@code writeOnly}, so once saved it is never shown again - not to clients, not in the
     * change history. Saving a device without it keeps the stored one.
     */
    public SettingsSchema secret(String name, String description, boolean mandatory) {
        properties.put(name, new LinkedHashMap<>(Map.of("type", "string", "minLength", 1, "writeOnly", true, "format", "password",
                "description", description)));
        if (mandatory) required.add(name);
        return this;
    }

    /** One of a few values, e.g. the SNMP version. */
    public SettingsSchema choice(String name, String description, List<String> options, String defaultValue, boolean mandatory) {
        Map<String, Object> property = new LinkedHashMap<>();
        property.put("type", "string");
        property.put("enum", List.copyOf(options));
        if (defaultValue != null) property.put("default", defaultValue);
        property.put("description", description);
        properties.put(name, property);
        if (mandatory) required.add(name);
        return this;
    }

    public SettingsSchema bool(String name, String description, boolean defaultValue) {
        properties.put(name, new LinkedHashMap<>(Map.of("type", "boolean", "default", defaultValue, "description", description)));
        return this;
    }

    public SettingsSchema integer(String name, String description, int minimum, int maximum, Integer defaultValue, boolean mandatory) {
        Map<String, Object> property = new LinkedHashMap<>();
        property.put("type", "integer");
        property.put("minimum", minimum);
        property.put("maximum", maximum);
        if (defaultValue != null) property.put("default", defaultValue);
        property.put("description", description);
        properties.put(name, property);
        if (mandatory) required.add(name);
        return this;
    }

    public Map<String, Object> build() {
        Map<String, Object> schema = new LinkedHashMap<>();
        schema.put("$schema", "https://json-schema.org/draft/2020-12/schema");
        schema.put("type", "object");
        schema.put("properties", properties);
        schema.put("required", List.copyOf(required));
        schema.put("additionalProperties", false);
        return schema;
    }
}
