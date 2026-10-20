package io.github.vojke98.phase.driver.api;

/**
 * How far a value can be trusted - the three severities of an
 * <a href="https://reference.opcfoundation.org/Core/Part8/v104/docs/">OPC UA StatusCode</a> (Part 8): every live and recorded value
 * carries one, so nobody mistakes a frozen or missing reading for a fresh one.
 */
public enum Quality {
    /** Read from the device just now. */
    GOOD,
    /** Usable with care: the device said so, or it is the last value seen during a short interruption. */
    UNCERTAIN,
    /** No usable value: the device does not have the address, or it has not answered. */
    BAD
}
