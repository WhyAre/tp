package seedu.address.model.tutorial;

import java.util.Objects;

import seedu.address.model.attendance.Attendance;
import seedu.address.model.uniquelist.Identifiable;
import seedu.address.model.uniquelist.UniqueList;

/**
 * Represents a tutorial
 */
public record Tutorial(String name, UniqueList<Attendance> attendances,
                UniqueList<Assignment> assignments) implements Identifiable<Tutorial> {

    public Tutorial(String name) {
        this(name, new UniqueList<>(), new UniqueList<>());
    }

    /**
     * Creates a new {@code Tutorial} object
     *
     * @param name
     *            name of the tutorial
     * @param assignments
     *            list of assignments
     */
    public Tutorial {
        Objects.requireNonNull(name);
        if (!isValidName(name)) {
            throw new IllegalArgumentException("Tutorial name is invalid.");
        }
    }

    /**
     * Adds assignments to tutorial
     *
     * @param assignment
     *            Assignment to add
     */
    public boolean addAssignment(Assignment assignment) {
        Objects.requireNonNull(assignment);
        return assignments.add(assignment);
    }

    public boolean hasAssignment(Assignment assignment) {
        return assignments.contains(assignment);
    }

    /**
     * Checks whether the given name is valid
     */
    public static boolean isValidName(String name) {
        Objects.requireNonNull(name);
        final var pattern = "[a-zA-Z0-9_-]+";

        return name.matches(pattern);
    }

    /**
     * Adds attendance
     */
    public void addAttendance(Attendance attendance) {
        this.attendances.add(attendance);
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
