package waypoint.model;

/** Distinct id type for vehicles so it cannot be swapped with other ids. */
public record VehicleId(String value) {
    public VehicleId {
        value = Ids.requireNonBlank(value, "VehicleId");
    }
}
