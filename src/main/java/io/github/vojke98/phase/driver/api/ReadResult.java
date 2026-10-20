package io.github.vojke98.phase.driver.api;

import java.time.Instant;

/**
 * What a device answered for one address.
 *
 * @param value     a {@link Boolean}, a {@link Number}, a {@link String} or a {@link java.util.List} of those; {@code null} when there
 *                  is none
 * @param quality   how far the value can be trusted
 * @param timestamp when the device took the value, if it says so (OPC UA's "source timestamp"); {@code null}: when it was read
 * @param message   why the quality is not GOOD, in a few words ("no such register"), for people looking at the tag
 */
public record ReadResult(Object value, Quality quality, Instant timestamp, String message) {

    public ReadResult {
        if (quality == null) throw new IllegalArgumentException("A read result needs a quality");
    }

    public static ReadResult good(Object value) {
        return new ReadResult(value, Quality.GOOD, null, null);
    }

    /** A value with the time the device took it. */
    public static ReadResult good(Object value, Instant timestamp) {
        return new ReadResult(value, Quality.GOOD, timestamp, null);
    }

    public static ReadResult uncertain(Object value, String message) {
        return new ReadResult(value, Quality.UNCERTAIN, null, message);
    }

    /** No value for this address - the device does not have it, refused it, ... (the device itself answered). */
    public static ReadResult bad(String message) {
        return new ReadResult(null, Quality.BAD, null, message);
    }
}
