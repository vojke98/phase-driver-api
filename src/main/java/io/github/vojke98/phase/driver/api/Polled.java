package io.github.vojke98.phase.driver.api;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * A device that answers when asked. The server asks for the tags that are due - each at its own rate - and for a value it needs at
 * once (what a write left behind, a check of the device, a value a {@link Pushing} driver asked to have read again).
 */
public interface Polled extends Driver {

    /**
     * A result for every address asked for (an address left out of the answer counts as BAD). Read as many at once as the protocol
     * allows - in as few messages as fit.
     */
    Map<String, ReadResult> read(Set<String> addresses) throws IOException;
}
