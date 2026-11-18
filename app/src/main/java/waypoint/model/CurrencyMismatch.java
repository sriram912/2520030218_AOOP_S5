package waypoint.model;

import java.util.Currency;

/** Thrown when attempting arithmetic or comparison across different currencies. */
public final class CurrencyMismatch extends RuntimeException {
    public CurrencyMismatch(Currency expected, Currency actual) {
        super("Currency mismatch: " + expected + " vs " + actual);
    }
}
