package seedu.address.model.submission;

import seedu.address.model.student.Student;
import seedu.address.model.tutorial.Assignment;
import seedu.address.model.uniquelist.Identifiable;

/**
 * Represents a submission made by the student
 */
public class Submission implements Identifiable<Submission> {
    private final Assignment assignment;
    private final Student student;
    private SubmissionStatus status;

    /**
     * Constructs a new {@link Submission} object
     */
    public Submission(Assignment assignment, Student student, SubmissionStatus status) {
        this.assignment = assignment;
        this.student = student;
        this.status = status;
    }

    public Assignment assignment() {
        return assignment;
    }

    public Student student() {
        return student;
    }

    public SubmissionStatus status() {
        return status;
    }

    public void setStatus(SubmissionStatus status) {
        this.status = status;
    }

    @Override
    public boolean hasSameIdentity(Submission other) {
        return assignment.hasSameIdentity(other.assignment) && student.hasSameIdentity(other.student);
    }
}
