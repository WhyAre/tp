package seedu.address.model.student;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Student's details in the address book. Guarantees: immutable;
 * can be empty or valid as declared in {@link #isValidDetails(String)}
 */
public class Details {
    /**
     * The value representing the student's details.
     */
    public final String value;

    /**
     * Constructs a {@code Details} object.
     *
     * @param details
     *            Any string representing details. Cannot be null.
     */
    public Details(String details) {
        requireNonNull(details);
        value = details;
    }

    /**
     * Returns the string representation of the details.
     *
     * @return The details as a string.
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Compares this {@code Details} object to another object for equality.
     *
     * @param other
     *            The object to compare to.
     * @return True if both objects have the same details value, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Details)) {
            return false;
        }

        Details otherDetails = (Details) other;
        return value.equals(otherDetails.value);
    }

    /**
     * Returns the hash code of this {@code Details} object.
     *
     * @return The hash code based on the details value.
     */
    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
