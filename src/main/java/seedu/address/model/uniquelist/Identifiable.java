package seedu.address.model.uniquelist;

/**
 * Represents an entity with unique identifier(s). Implementing classes must
 * provide a way to compare identities.
 */
public interface Identifiable<T> {
    /**
     * Checks if an entity has the same identifier as the other entity
     */
    boolean hasSameIdentity(T other);
}
