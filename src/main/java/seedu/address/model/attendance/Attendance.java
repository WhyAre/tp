package seedu.address.model.attendance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seedu.address.model.uniquelist.Identifiable;

public record Attendance(String tutorialName, Map<String, List<Integer>> attendances) implements Identifiable<Attendance> {
    private static final int NUMBER_OF_WEEKS = 11;
    private static final int ABSENT = 0;
    private static final int PRESENT = 1;

    public Attendance(String tutorialName) {
        this(tutorialName, Map.copyOf(new HashMap<>()));
    }

    /**
     * Creates an attendance record for a student for the specified tutorial
     * @param name Name of student
     * @return An Attendance object containing new student attendance record
     */
    public Attendance addStudent(String name) {
        List<Integer> attendance = new ArrayList<>(Collections.nCopies(NUMBER_OF_WEEKS, ABSENT));
        Map<String, List<Integer>> newAttendances = new HashMap<>(attendances);
        newAttendances.put(name, attendance);
        return new Attendance(tutorialName, Map.copyOf(newAttendances));
    }

    /**
     * Marks the attendance of the student for the specified week
     * @param week Week of attendance
     * @param name Name of student to mark attendance for
     * @return new Attendance object with updated attendance
     */
    public Attendance markAttendance(int week, String name) {
        Map<String, List<Integer>> newAttendances = new HashMap<>(attendances);
        List<Integer> attendance = new ArrayList<>(newAttendances.get(name));
        attendance.set(week - 2, PRESENT);
        newAttendances.put(name, List.copyOf(attendance));
        return new Attendance(tutorialName, Map.copyOf(newAttendances));
    }

    /**
     * Returns a clone of the current attendance.
     */
    public Attendance clone() {
        return new Attendance(tutorialName, attendances);
    }

    @Override
    public boolean hasSameIdentity(Attendance other) {
        if (other == null) {
            return false;
        }

        return this.tutorialName.equals(other.tutorialName);
    }
}
