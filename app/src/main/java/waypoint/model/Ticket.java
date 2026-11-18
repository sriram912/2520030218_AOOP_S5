package waypoint.model;

import java.time.Instant;
import java.util.Objects;

/**
 * ENTITY. Invariants:
 *   I1 entry is always present
 *   I2 exit is null, or not before entry
 *   I3 fare is null, or not negative
 *   I4 fare exists only if exit exists
 *   I5 a closed ticket never reopens
 */
public final class Ticket {
    private final TicketId id;
    private final SpotId spot;
    private final Instant entry;
    private Instant exit; // mutable: the domain genuinely changes it, once
    private Money fare;   // mutable: set together with exit, never alone

    private Ticket(TicketId id, SpotId spot, Instant entry) {
        this.id = Objects.requireNonNull(id, "id");
        this.spot = Objects.requireNonNull(spot, "spot");
        this.entry = Objects.requireNonNull(entry, "entry");
    }

    public static Ticket open(TicketId id, SpotId spot, Instant entry) {
        return new Ticket(id, spot, entry);
    }

    public boolean isOpen() {
        return exit == null;
    }

    public TicketId id() {
        return id;
    }

    public SpotId spot() {
        return spot;
    }

    public Instant entry() {
        return entry;
    }

    public Instant exit() {
        return exit;
    }

    public Money fare() {
        return fare;
    }

    /**
     * PRE : at >= entry; this ticket is open
     * POST: exit == at and fare is set, together; ticket is closed forever
     */
    public Money close(Instant at, FarePolicy policy) {
        Objects.requireNonNull(at, "exit time");
        Objects.requireNonNull(policy, "fare policy");
        if (!isOpen()) {
            throw new TicketAlreadyClosed(id);
        }
        if (at.isBefore(entry)) {
            throw new IllegalArgumentException("exit " + at + " before entry " + entry);
        }

        Money computed = Objects.requireNonNull(policy.fareFor(entry, at, spot), "fare");
        if (computed.isNegative()) {
            throw new IllegalStateException("negative fare from " + policy);
        }

        this.exit = at;
        this.fare = computed;
        return computed;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Ticket t) && t.id.equals(id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
