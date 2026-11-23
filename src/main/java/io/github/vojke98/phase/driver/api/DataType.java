package io.github.vojke98.phase.driver.api;

/** How a tag's value is represented on the device; decides what a write may contain. */
public enum DataType {
    BOOLEAN,
    INT16,
    INT32,
    FLOAT32,
    /**
     * Text: read and written as a {@link String}. A protocol whose text has a fixed room on the device (a run of Modbus registers)
     * says how many characters fit - {@link AddressCheck#maxLength()}, {@link TagInfo#maxLength()}; the server refuses a longer
     * write before it reaches the driver.
     */
    STRING
}
