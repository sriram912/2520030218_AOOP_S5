package waypoint.model;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Objects;

/** VALUE OBJECT. Exact money in integer minor units (paise) with currency. */
public final class Money implements Comparable<Money> {
    public static final Currency INR = Currency.getInstance("INR");
    public static final Currency USD = Currency.getInstance("USD");

    private final long minor;
    private final Currency currency;

    private Money(long minor, Currency currency) {
        this.minor = minor;
        this.currency = Objects.requireNonNull(currency, "currency");
    }

    public static Money ofMinor(long paise, Currency c) {
        return new Money(paise, c);
    }

    public static Money rupees(long r) {
        return new Money(Math.multiplyExact(r, 100L), INR);
    }

    public long minor() {
        return minor;
    }

    public Currency currency() {
        return currency;
    }

    public Money plus(Money o) {
        same(o);
        return new Money(Math.addExact(minor, o.minor), currency);
    }

    public Money minus(Money o) {
        same(o);
        return new Money(Math.subtractExact(minor, o.minor), currency);
    }

    public Money times(int n) {
        return new Money(Math.multiplyExact(minor, n), currency);
    }

    public boolean isNegative() {
        return minor < 0;
    }

    /** Shares sum EXACTLY to this amount; the remainder goes to the first few. */
    public List<Money> allocate(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive");
        }
        long base = Math.floorDiv(minor, n);
        long rem = Math.floorMod(minor, n);
        List<Money> out = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            out.add(new Money(base + (i < rem ? 1 : 0), currency));
        }
        return List.copyOf(out);
    }

    private void same(Money o) {
        Objects.requireNonNull(o, "other money");
        if (!currency.equals(o.currency)) {
            throw new CurrencyMismatch(currency, o.currency);
        }
    }

    @Override
    public int compareTo(Money o) {
        same(o);
        return Long.compare(minor, o.minor);
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Money m) && m.minor == minor && m.currency.equals(currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(minor, currency);
    }

    @Override
    public String toString() {
        String sign = minor < 0 ? "-" : "";
        long absMinor = Math.abs(minor);
        return sign + currency.getSymbol() + (absMinor / 100) + "." + String.format("%02d", absMinor % 100);
    }
}
