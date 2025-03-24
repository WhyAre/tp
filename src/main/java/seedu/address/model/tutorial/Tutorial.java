package seedu.address.model.tutorial;

import seedu.address.model.uniquelist.Identifiable;

/**
 * Represents a tutorial
 */
public record Tutorial(String name) implements Identifiable<Tutorial> {

    /**
     * Creates a new Tutorial object
     *
     * @param name
     *            name of the tutorial
     */
    public Tutorial {
        if (!isValidName(name)) {
            throw new IllegalArgumentException("Name cannot be empty!");
        }
    }

    /**
     * Checks whether the given name is valid
     */
    public static boolean isValidName(String name) {
        final var pattern = "[a-zA-Z0-9_-]+";

        return name.matches(pattern);
    }

    @Override
    public boolean hasSameIdentity(Tutorial other) {
        if (other == null) {
            return false;
        }

        return this.name.equals(other.name);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
