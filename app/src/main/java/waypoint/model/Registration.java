package waypoint.model;

import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

/** VALUE OBJECT. Validated and normalised vehicle registration number. */
public record Registration(String value) {
    // Format: two letters, two digits, one or two letters, four digits. e.g. MH12AB1234
    private static final Pattern FORMAT = Pattern.compile("[A-Z]{2}\\d{2}[A-Z]{1,2}\\d{4}");

    public Registration {
        Objects.requireNonNull(value, "registration required");
        value = value.trim().toUpperCase(Locale.ROOT);
        if (!FORMAT.matcher(value).matches()) {
            throw new IllegalArgumentException("bad registration: " + value);
        }
    }

    @Override
    public String toString() {
        return value;
    }
}
