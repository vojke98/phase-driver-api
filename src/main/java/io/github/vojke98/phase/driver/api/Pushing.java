package io.github.vojke98.phase.driver.api;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * A device that reports changes itself - OPC UA monitored items, MQTT messages, an alarm panel's events. The driver hands each
 * change to {@link DeviceSession#report} as it arrives, so it reaches screens, alarms and history at once; nobody asks in between.
 * <p>
 * A driver may also be {@link Polled}: the tags it does not take on in {@link #monitor} are then asked for by the server.
 */
public interface Pushing extends Driver {

    /**
     * The tags wanted now, with how often and how finely each is wanted - the whole set each time, in place of the one before (an
     * empty one: none). Called after {@link #open} and whenever the wanted tags change.
     * <p>
     * Every tag it takes on gets a first value soon after - its current value, or a BAD status ({@link StatusCode#BAD_WAITING_FOR_INITIAL_DATA}
     * when none is known yet) - so nothing waits for its first change.
     *
     * @return the tags this driver reports itself; the others are read by the server when the driver is also {@link Polled}, and
     *         are BAD otherwise
     * @throws IOException when the device would not take the subscription - as a failed call
     */
    Set<String> monitor(Map<String, Sampling> wanted) throws IOException;
}
