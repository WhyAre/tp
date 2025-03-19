package seedu.address.model.tutorial;

import java.util.List;
import java.util.Objects;

import seedu.address.model.student.Student;

/**
 * Represents a tutorial along with its associated students.
 */
public class TutorialWithStudents {
    private final Tutorial tutorial;
    private final List<Student> students;

    /**
     * Constructs a {@code TutorialWithStudents} object
     *
     * @param tutorial The tutorial associated with the students
     * @param students The list of students enrolled in the tutorial
     */
    public TutorialWithStudents(Tutorial tutorial, List<Student> students) {
        this.tutorial = tutorial;
        this.students = students;
    }

    /**
     * Returns the tutorial
     *
     * @return The tutorial object
     */
    public Tutorial getTutorial() {
        return tutorial;
    }

    /**
     * Returns the list of students in the tutorial
     *
     * @return A list of students associated with the tutorial
     */
    public List<Student> getStudents() {
        return students;
    }

    /**
     * Two {@code TutorialWithStudents} objects are considered equal if they have the same {@code tutorial}
     * and the same list of {@code students}. This means both the tutorial and the students list must be
     * identical in terms of their content and order.
     *
     * @param o the reference object with which to compare.
     * @return {@code true} if this object is the same as the {@code o} argument; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TutorialWithStudents that = (TutorialWithStudents) o;
        return Objects.equals(tutorial, that.tutorial);
    }

    /**
     * Returns a hash code value for the object
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(tutorial, students);
    }
}
