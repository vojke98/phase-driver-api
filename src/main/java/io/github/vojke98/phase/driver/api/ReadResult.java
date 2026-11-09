package io.github.vojke98.phase.driver.api;

import java.time.Instant;

/**
 * One value of a device, read or reported (OPC UA's DataValue): the value, why it is as good as it is, and when the device took it.
 *
 * @param value     a {@link Boolean}, a {@link Number}, a {@link String} or a {@link java.util.List} of those; {@code null} when there
 *                  is none
 * @param status    how far the value can be trusted, and why
 * @param timestamp when the device took the value, if it says so (OPC UA's "source timestamp"); {@code null}: when it arrived
 * @param message   why the status is not {@link StatusCode#GOOD}, in a few words ("no such register"), for people looking at the tag
 */
public record ReadResult(Object value, StatusCode status, Instant timestamp, String message) {

    public ReadResult {
        if (status == null) throw new IllegalArgumentException("A read result needs a status");
    }

    public Quality quality() {
        return status.quality();
    }

    public static ReadResult good(Object value) {
        return new ReadResult(value, StatusCode.GOOD, null, null);
    }

    /** A value with the time the device took it. */
    public static ReadResult good(Object value, Instant timestamp) {
        return new ReadResult(value, StatusCode.GOOD, timestamp, null);
    }

    /** A value the device itself says is doubtful. */
    public static ReadResult uncertain(Object value, String message) {
        return new ReadResult(value, StatusCode.UNCERTAIN, null, message);
    }

    /** No value: the device answered, but has none for it ({@link StatusCode#BAD}). */
    public static ReadResult bad(String message) {
        return new ReadResult(null, StatusCode.BAD, null, message);
    }

    /** No value, for the reason {@code status} gives (one of the BAD codes). */
    public static ReadResult bad(StatusCode status, String message) {
        if (status.quality() != Quality.BAD) throw new IllegalArgumentException(status + " is not a BAD status");
        return new ReadResult(null, status, null, message);
    }
}
