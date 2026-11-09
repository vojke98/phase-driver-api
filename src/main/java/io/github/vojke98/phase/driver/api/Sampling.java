package io.github.vojke98.phase.driver.api;

/**
 * How a tag is wanted ({@link Pushing#monitor}): a device that can be told so (OPC UA's sampling interval and deadband filter) reports
 * less; one that cannot ignores it. The server applies the deadband to whatever comes, either way.
 *
 * @param intervalMs how often the value is wanted at most (ms)
 * @param deadband   how much a number must change to count as a change (absolute), or {@code null} for any change
 */
public record Sampling(long intervalMs, Double deadband) {

    public Sampling {
        if (intervalMs <= 0) throw new IllegalArgumentException("A sampling interval is positive");
        if (deadband != null && deadband < 0) throw new IllegalArgumentException("A deadband is not negative");
    }
}
