package waypoint.model;

import java.util.Objects;

/**
 * ENTITY. Identity = id. Invariant: a spot cannot be assigned while occupied.
 */
public final class Spot {
    private final SpotId id;
    private VehicleId occupant; // the ONLY mutable field

    public Spot(SpotId id) {
        this.id = Objects.requireNonNull(id, "id");
    }

    /** POST: returns true iff THIS call took the spot. */
    public boolean tryAssign(VehicleId v) {
        Objects.requireNonNull(v, "vehicle");
        if (occupant != null) {
            return false;
        }
        occupant = v;
        return true;
    }

    /** Idempotent: releasing a free spot, or one held by someone else, is not an error. */
    public void release(VehicleId v) {
        if (occupant != null && occupant.equals(v)) {
            occupant = null;
        }
    }

    public boolean isFree() {
        return occupant == null;
    }

    public SpotId id() {
        return id;
    }

    // ENTITY: identity is the id alone, so state changes never change identity.
    @Override
    public boolean equals(Object o) {
        return (o instanceof Spot s) && s.id.equals(id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
