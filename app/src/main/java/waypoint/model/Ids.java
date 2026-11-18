package waypoint.model;

/**
 * Shared validation helper for distinct domain id types
 * ({@link TicketId}, {@link SpotId}, {@link VehicleId}) so they can never be swapped at a call site.
 */
public final class Ids {
    private Ids() {
    }

    static String requireNonBlank(String value, String typeName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(typeName + " must not be null or blank");
        }
        return value;
    }
}
