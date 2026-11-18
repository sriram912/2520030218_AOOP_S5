package waypoint.model;

import static org.junit.jupiter.api.Assertions.*;
import static waypoint.model.Money.INR;
import static waypoint.model.Money.USD;

import org.junit.jupiter.api.Test;

class MoneyTest {
    @Test void tenPaiseAddedHundredTimesIsExactlyOneRupee() {
        Money sum = Money.ofMinor(0, INR);
        for (int i = 0; i < 100; i++) sum = sum.plus(Money.ofMinor(10, INR));
        assertEquals(Money.rupees(10), sum);
    }
    @Test void equalAmountsAreEqualAndHashAlike() {
        assertEquals(Money.rupees(120), Money.ofMinor(12000, INR));
        assertEquals(Money.rupees(120).hashCode(), Money.ofMinor(12000, INR).hashCode());
    }
    @Test void mixingCurrenciesThrows() {
        assertThrows(CurrencyMismatch.class, () -> Money.rupees(10).plus(Money.ofMinor(10, USD)));
    }
    @Test void allocationLosesNoPaise() {
        var shares = Money.rupees(100).allocate(3);
        assertEquals(3, shares.size());
        assertEquals(Money.rupees(100), shares.stream().reduce(Money.ofMinor(0, INR), Money::plus));
    }
    @Test void moneyIsImmutable() {
        Money a = Money.rupees(10);
        a.plus(Money.rupees(5));
        assertEquals(Money.rupees(10), a);        // a must be unchanged
    }
}
