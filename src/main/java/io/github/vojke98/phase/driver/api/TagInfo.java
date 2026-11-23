package io.github.vojke98.phase.driver.api;

/**
 * One tag as a device itself describes it (see {@link Driver#browse()}).
 *
 * @param name        its address, as reads and writes use it
 * @param arraySize   {@code null} for a single value, otherwise the number of elements
 * @param description what the device says about it, or {@code null}
 * @param maxLength   for a {@link DataType#STRING}: the most characters it holds, or {@code null} when the device sets no limit
 */
public record TagInfo(String name, DataType dataType, Integer arraySize, String description, Integer maxLength) {

    public TagInfo(String name, DataType dataType, Integer arraySize, String description) {
        this(name, dataType, arraySize, description, null);
    }

    public TagInfo(String name, DataType dataType, Integer arraySize) {
        this(name, dataType, arraySize, null, null);
    }
}
