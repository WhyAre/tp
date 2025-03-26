package seedu.address.model.tutorial;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.attendance.Attendance;
import seedu.address.model.uniquelist.Identifiable;

/**
 * Represents a tutorial
 */
public record Tutorial(String name, List<Attendance> attendances) implements Identifiable<Tutorial> {

    public Tutorial(String name) {
        this(name, new ArrayList<>());
    }
    /**
     * Checks whether the given name is valid
     */
    public static boolean isValidName(String name) {
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
