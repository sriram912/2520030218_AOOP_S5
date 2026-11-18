package waypoint.model;

/** Distinct id type for parking spots so it cannot be swapped with other ids. */
public record SpotId(String value) {
    public SpotId {
        value = Ids.requireNonBlank(value, "SpotId");
    }
}
