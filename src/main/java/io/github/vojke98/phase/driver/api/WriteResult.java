package io.github.vojke98.phase.driver.api;

/**
 * What a device did with one value of a write.
 *
 * @param message      why it was not written, in a few words ("read only", "illegal data address"), shown to the person who wrote
 * @param codeRequired it was not written because it needs a code a person types ({@link Driver#write(java.util.Map, String)}):
 *                     the server asks for one and sends the write again with it
 */
public record WriteResult(boolean success, String message, boolean codeRequired) {

    private static final WriteResult OK = new WriteResult(true, null, false);

    /** A result that asks for no code - every result but {@link #codeRequired(String)}. */
    public WriteResult(boolean success, String message) {
        this(success, message, false);
    }

    public static WriteResult ok() {
        return OK;
    }

    /** The device answered and refused this value (or the address is not one it has). */
    public static WriteResult refused(String message) {
        return new WriteResult(false, message, false);
    }

    /**
     * Not written: it needs a code a person types (none was given, or the driver keeps none). {@code message} says what for
     * ("the code to disarm area 1").
     */
    public static WriteResult codeRequired(String message) {
        return new WriteResult(false, message, true);
    }
}
