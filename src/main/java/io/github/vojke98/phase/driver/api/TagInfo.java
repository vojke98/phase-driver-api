package io.github.vojke98.phase.driver.api;

/**
 * One tag as a device itself describes it (see {@link DeviceDriver#browse()}).
 *
 * @param name        its address, as reads and writes use it
 * @param arraySize   {@code null} for a single value, otherwise the number of elements
 * @param description what the device says about it, or {@code null}
 */
public record TagInfo(String name, DataType dataType, Integer arraySize, String description) {

    public TagInfo(String name, DataType dataType, Integer arraySize) {
        this(name, dataType, arraySize, null);
    }
}
