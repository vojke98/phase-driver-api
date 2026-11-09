package io.github.vojke98.phase.driver.api;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * The connection to one device - the protocol and nothing else. The server runs every device on a thread of its own and calls its
 * driver from that thread only, one call at a time; it decides when to open, when to read, when to try again after a failure, and
 * which values count as changes. A driver keeps no schedule, no reconnect loop and no cache of its own.
 * <p>
 * How the values get to the server is what a driver adds to this:
 * <ul>
 *   <li>{@link Polled} - the device answers when asked: the server asks, at each tag's rate (Modbus, SNMP, A-bus);</li>
 *   <li>{@link Pushing} - the device reports changes itself: the driver hands them over as they come (OPC UA subscriptions, MQTT,
 *       an alarm panel's events), and nobody asks in between;</li>
 *   <li>both - a device that reports what it can and answers for the rest.</li>
 * </ul>
 * Addresses are the device's own names for its tags (what comes after {@code device/} in a tag path): a variable, an OID, a register
 * such as {@code HR100:float32}. Values are a {@link Boolean}, a {@link Number}, a {@link String} or a {@link List} of those.
 * <p>
 * Two kinds of failure, as in OPC UA: an {@link IOException} means the device did not answer (the server counts it, and after a few
 * in a row closes the driver and opens it again later); a device that answered but would not do something for one address says so
 * in that address's result.
 */
public interface Driver {

    /**
     * Connects. {@code session} is how the driver reaches the server from then on - pushed values, a lost connection - from any
     * thread, until {@link #close()}.
     *
     * @throws IOException when the device cannot be reached - the server tries again later
     */
    void open(DeviceSession session) throws IOException;

    /** Disconnects; never throws. The server may open the driver again afterwards. */
    void close();

    /**
     * Writes these values and says, per address, whether it was written (OPC UA's Write does the same). A value that cannot be sent -
     * an address that is not one, a value that does not fit - is refused on its own, before anything is sent; the others are written.
     */
    Map<String, WriteResult> write(Map<String, Object> values) throws IOException;

    /**
     * The tags the device offers - the basis for importing them into the tag registry. Called while open. Only for protocols whose
     * provider says {@link DriverProvider#canBrowse()}.
     */
    default List<TagInfo> browse() throws IOException {
        throw new UnsupportedOperationException("This protocol cannot list its tags");
    }
}
