package io.github.vojke98.phase.driver.api;

/**
 * Why a value is as good as it is - the reason behind its {@link Quality}, after OPC UA's StatusCodes (Part 4, 7.39): an alarm or a
 * trend can then say "the device does not answer" rather than only "bad". Drivers use the codes about the device and its values;
 * the server sets the ones about the connection itself when it has to ({@link #BAD_NOT_CONNECTED}, {@link #BAD_OUT_OF_SERVICE}, ...).
 */
public enum StatusCode {

    /** The value as the device has it now. */
    GOOD(Quality.GOOD),

    /** The device itself says the value is doubtful (a sensor out of its range, a substituted value). */
    UNCERTAIN(Quality.UNCERTAIN),
    /** The last value seen, kept during a short interruption: the device did not answer the latest request. */
    UNCERTAIN_LAST_USABLE_VALUE(Quality.UNCERTAIN),

    /** The device answered, but has no value for it (it says so without saying why). */
    BAD(Quality.BAD),
    /** The device does not have this address. */
    BAD_NO_SUCH_ADDRESS(Quality.BAD),
    /** The device reports a fault for this value: a broken sensor, a module that lost its panel. */
    BAD_DEVICE_FAILURE(Quality.BAD),
    /** Asked for, but no value has come yet (a subscription whose first value is still on its way). */
    BAD_WAITING_FOR_INITIAL_DATA(Quality.BAD),
    /** There is no connection to the device. */
    BAD_NOT_CONNECTED(Quality.BAD),
    /** The device stopped answering. */
    BAD_COMMUNICATION_ERROR(Quality.BAD),
    /** The settings of the device or the tag do not fit what the device has. */
    BAD_CONFIGURATION_ERROR(Quality.BAD),
    /** The device is not in use: disabled, removed, or its driver is missing. */
    BAD_OUT_OF_SERVICE(Quality.BAD);

    private final Quality quality;

    StatusCode(Quality quality) {
        this.quality = quality;
    }

    /** How far a value with this code can be trusted. */
    public Quality quality() {
        return quality;
    }
}
