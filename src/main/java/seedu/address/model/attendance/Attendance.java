package seedu.address.model.attendance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import seedu.address.model.student.Student;
import seedu.address.model.tutorial.Tutorial;
import seedu.address.model.uniquelist.Identifiable;

/**
 * Represents an attendance record
 */
public record Attendance(Tutorial tutorial, Student student,
                List<Integer> attendances) implements Identifiable<Attendance> {
    private static final int NUMBER_OF_WEEKS = 11;
    private static final int ABSENT = 0;
    private static final int PRESENT = 1;

    public Attendance(Tutorial tutorial, Student student) {
        this(tutorial, student, new ArrayList<>(Collections.nCopies(NUMBER_OF_WEEKS, ABSENT)));
    }

    /**
     * Marks attendance for the specific week
     *
     * @param week Week of attendance
     */
    public void markAttendance(int week) {
        attendances.set(week - 2, PRESENT);
    }

    @Override
    public boolean hasSameIdentity(Attendance other) {
        if (other == null) {
            return false;
        }

        return this.tutorial.equals(other.tutorial) && this.student.equals(other.student);
    }
}
