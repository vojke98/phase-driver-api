package io.github.vojke98.phase.driver.api;

import java.util.Map;

/**
 * A protocol, as a driver brings it. A driver is a jar the server finds in its drivers directories: it names its providers in
 * {@code META-INF/services/io.github.vojke98.phase.driver.api.DriverProvider}, is built against this API alone (scope provided) and carries
 * its own libraries inside; it gets a class loader of its own, so they never clash with the server's or another driver's.
 */
public interface DriverProvider {

    /** Return {@link DriverApi#VERSION}: compiled in, it tells the server which version of this API the driver was built against. */
    int apiVersion();

    /** The id devices store: {@link DriverApi#PROTOCOL_ID_PATTERN}, e.g. {@code SNMP}. */
    String protocol();

    /** The name people see, e.g. "SNMP (v1, v2c, v3)". */
    String displayName();

    /** The devices of this protocol can list their own tags ({@link DeviceDriver#browse()}). */
    default boolean canBrowse() {
        return false;
    }

    /**
     * The JSON Schema of a device's settings, built with {@link SettingsSchema}: the server checks the settings against it (unknown
     * settings are refused, required ones must be there, types and ranges hold) and fills in the defaults; clients build their form
     * from it. A setting marked {@code writeOnly} (a password) is never shown again once saved.
     */
    Map<String, Object> settingsSchema();

    /** How this protocol's addresses are written, in one line for the tag form - e.g. "HR100, HR100:float32, IR7, C12, DI3". */
    default String addressHelp() {
        return null;
    }

    /** Whether {@code address} is one of this protocol's, and what it says about the tag - without a device. */
    default AddressCheck checkAddress(String address) {
        return AddressCheck.unknown();
    }

    /**
     * A driver for one device; nothing connects yet ({@link DeviceDriver#connect()} comes later, on a polling thread).
     *
     * @param settings the device's settings, checked against {@link #settingsSchema()}, with the defaults of those left out filled
     *                 in: text is a String, an integer an Integer (or a Long), a number a Double, a boolean a Boolean
     * @throws IllegalArgumentException when the settings cannot work (an address that is not one, ...) - the message is shown
     */
    DeviceDriver create(Map<String, Object> settings);
}
