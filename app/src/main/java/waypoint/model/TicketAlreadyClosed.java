package waypoint.model;

/** Thrown when attempting to close a ticket that has already been closed. */
public final class TicketAlreadyClosed extends RuntimeException {
    public TicketAlreadyClosed(TicketId id) {
        super("Ticket already closed: " + id);
    }
}
