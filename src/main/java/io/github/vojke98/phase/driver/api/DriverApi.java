package io.github.vojke98.phase.driver.api;

import java.util.regex.Pattern;

/**
 * The version of this API, and the rules every driver follows.
 * <p>
 * A driver returns {@link #VERSION} from {@link DriverProvider#apiVersion()}: the number is compiled into the driver, so the server
 * can tell a driver built against another version of the API and refuse it with a clear message instead of failing on a missing
 * method later. Additions that old drivers can live with (a new default method) keep the version; anything else raises it.
 */
public final class DriverApi {

    /** This API's version. */
    public static final int VERSION = 1;

    /** What a protocol id looks like: upper case letters, digits and underscores, starting with a letter - e.g. {@code MODBUS}. */
    public static final String PROTOCOL_ID_PATTERN = "[A-Z][A-Z0-9_]{0,31}";
    private static final Pattern PROTOCOL_ID = Pattern.compile(PROTOCOL_ID_PATTERN);

    public static boolean isProtocolId(String id) {
        return id != null && PROTOCOL_ID.matcher(id).matches();
    }

    private DriverApi() {
    }
}
