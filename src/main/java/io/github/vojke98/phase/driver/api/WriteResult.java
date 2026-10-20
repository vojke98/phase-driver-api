package io.github.vojke98.phase.driver.api;

/**
 * What a device did with one value of a write.
 *
 * @param message why it was not written, in a few words ("read only", "illegal data address"), shown to the person who wrote
 */
public record WriteResult(boolean success, String message) {

    private static final WriteResult OK = new WriteResult(true, null);

    public static WriteResult ok() {
        return OK;
    }

    /** The device answered and refused this value (or the address is not one it has). */
    public static WriteResult refused(String message) {
        return new WriteResult(false, message);
    }
}
