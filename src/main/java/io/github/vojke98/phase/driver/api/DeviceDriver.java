package io.github.vojke98.phase.driver.api;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The connection to one device. The server calls it from one thread at a time per device (it holds the device's lock), connects
 * before the first read or write and again after a failure, and disconnects when the device is stopped, changed or its driver is
 * replaced.
 * <p>
 * Addresses are the device's own names for its tags (what comes after {@code device/} in a tag path): a variable, an OID, a register
 * such as {@code HR100:float32}. Values are a {@link Boolean}, a {@link Number}, a {@link String} or a {@link List} of those.
 * <p>
 * Two kinds of failure, as in OPC UA: an {@link IOException} means the device did not answer (it is counted against the device, and
 * after a few in a row its tags turn BAD and it is connected again); a device that answered but would not do something for one
 * address says so in that address's result.
 */
public interface DeviceDriver {

    /** @throws IOException when the device cannot be reached - the server tries again on the next poll */
    void connect() throws IOException;

    /** Never throws; the driver may be connected again afterwards. */
    void disconnect();

    boolean isConnected();

    /**
     * One poll: a result for every address asked for (an address left out of the answer counts as BAD). Read as many at once as the
     * protocol allows.
     */
    Map<String, ReadResult> read(Set<String> addresses) throws IOException;

    /**
     * Writes these values and says, per address, whether it was written (OPC UA's Write does the same). A value that cannot be sent -
     * an address that is not one, a value that does not fit - is refused on its own, before anything is sent; the others are written.
     */
    Map<String, WriteResult> write(Map<String, Object> values) throws IOException;

    /**
     * The tags the device offers - the basis for importing them into the tag registry. Needs a connection. Only for protocols whose
     * provider says {@link DriverProvider#canBrowse()}.
     */
    default List<TagInfo> browse() throws IOException {
        throw new UnsupportedOperationException("This protocol cannot list its tags");
    }
}
