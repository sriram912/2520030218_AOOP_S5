package waypoint.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SpotTest {
    @Test void aSpotCannotBeAssignedTwice() {
        Spot s = new Spot(new SpotId("S1"));
        assertTrue(s.tryAssign(new VehicleId("V1")));
        assertFalse(s.tryAssign(new VehicleId("V2")), "second claim must fail, not overwrite");
    }
    @Test void releaseIsIdempotent() {
        Spot s = new Spot(new SpotId("S1"));
        s.release(new VehicleId("V1"));      // never assigned — must not throw
        assertTrue(s.isFree());
    }
    @Test void spotIdentityIsItsIdNotItsState() {
        Spot a = new Spot(new SpotId("S1"));
        Spot b = new Spot(new SpotId("S1"));
        a.tryAssign(new VehicleId("V1"));
        assertEquals(a, b, "an entity stays the same entity when its state changes");
    }
}
