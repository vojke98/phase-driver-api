package io.github.vojke98.phase.driver.api;

import java.util.Map;
import java.util.Set;

/**
 * The server's side of an open driver: what the driver tells it without being asked. Every method may be called from any thread -
 * the driver's own reader thread, a library's callback - and returns at once: the server takes it over on threads of its own.
 */
public interface DeviceSession {

    /** Values that changed, by address - only those the driver took on in {@link Pushing#monitor}. The latest wins. */
    void report(Map<String, ReadResult> values);

    /**
     * The connection broke (the device closed it, a session timed out). The server closes the driver, marks its tags BAD, and opens it
     * again later.
     */
    void lost(String reason);

    /**
     * These tags' values may be stale - the device restarted, or was away for a while: the server reads them again soon. Only for a
     * driver that is also {@link Polled}.
     */
    void refresh(Set<String> addresses);
}
