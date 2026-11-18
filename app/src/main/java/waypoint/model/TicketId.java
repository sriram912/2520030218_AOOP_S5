package waypoint.model;

/** Distinct id type for tickets so it cannot be swapped with other ids. */
public record TicketId(String value) {
    public TicketId {
        value = Ids.requireNonBlank(value, "TicketId");
    }
}
