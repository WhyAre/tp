package seedu.address.model.tutorial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import seedu.address.model.uniquelist.Identifiable;

/**
 * Represents a tutorial
 */
public record Tutorial(String name, List<Attendance> attendances) implements Identifiable<Tutorial> {
    private static final int NUMBER_OF_WEEKS = 11;
    private static final int ABSENT = 0;

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
     * Creates an attendance record for a student for this tutorial
     *
     * @param studentName Name of student
     * @return An attendance object
     */
    public Tutorial addAttendanceSlot(String studentName) {
        List<Attendance> newAttendanceList = new ArrayList<>(attendances);
        Attendance newAttendance = new Attendance(studentName, new ArrayList<>(Collections.nCopies(NUMBER_OF_WEEKS, ABSENT)));
        newAttendanceList.add(newAttendance);
        return new Tutorial(name, List.copyOf(newAttendanceList));
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
