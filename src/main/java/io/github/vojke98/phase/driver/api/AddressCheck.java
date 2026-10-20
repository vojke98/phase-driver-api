package io.github.vojke98.phase.driver.api;

/**
 * What a protocol can tell about an address before it is ever read: whether it is one at all, and - when the address says so, as
 * {@code HR100:float32} does - its type and whether it can be written. The tag registry uses it to refuse a typo when a tag is saved
 * and to suggest the type.
 *
 * @param problem  why it is not an address, or {@code null}
 * @param dataType the type the address implies, or {@code null} when it does not say
 * @param writable whether the address can be written, or {@code null} when it does not say
 */
public record AddressCheck(String problem, DataType dataType, Boolean writable) {

    private static final AddressCheck ANY = new AddressCheck(null, null, null);

    /** Any name might be an address of this protocol (a PLC variable): only the device can tell. */
    public static AddressCheck unknown() {
        return ANY;
    }

    public static AddressCheck valid(DataType dataType, Boolean writable) {
        return new AddressCheck(null, dataType, writable);
    }

    public static AddressCheck invalid(String problem) {
        return new AddressCheck(problem, null, null);
    }

    public boolean isValid() {
        return problem == null;
    }
}
