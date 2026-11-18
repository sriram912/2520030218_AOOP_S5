package waypoint.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import org.junit.jupiter.api.Test;

class TicketTest {
    static final Instant T10 = Instant.parse("2026-03-01T10:00:00Z");
    static final Instant T09 = Instant.parse("2026-03-01T09:00:00Z");
    static final Instant T12 = Instant.parse("2026-03-01T12:00:00Z");
    static final FarePolicy FLAT = (e, x, s) -> Money.rupees(40);

    @Test void exitBeforeEntryIsImpossible() {
        Ticket t = Ticket.open(new TicketId("T1"), new SpotId("S1"), T10);
        assertThrows(IllegalArgumentException.class, () -> t.close(T09, FLAT));
        assertTrue(t.isOpen(), "a rejected close must leave the ticket untouched");
    }
    @Test void closingTwiceIsRejected() {
        Ticket t = Ticket.open(new TicketId("T1"), new SpotId("S1"), T10);
        t.close(T12, FLAT);
        assertThrows(TicketAlreadyClosed.class, () -> t.close(T12, FLAT));
    }
    @Test void fareAndExitAppearTogether() {
        Ticket t = Ticket.open(new TicketId("T1"), new SpotId("S1"), T10);
        assertNull(t.fare());
        assertEquals(Money.rupees(40), t.close(T12, FLAT));
        assertFalse(t.isOpen());
    }
    @Test void classExposesNoSetters() {   // reflection: the constraint is testable
        assertTrue(java.util.Arrays.stream(Ticket.class.getMethods())
            .noneMatch(m -> m.getName().startsWith("set")));
    }
}
