package seedu.address.model.submission;

import java.util.Objects;

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

    public Submission(Submission submission) {
        this(submission.assignment, submission.student, submission.status);
    }

    public Submission setAssignment(Assignment assignment) {
        return new Submission(assignment, student, status);
    }

    public Submission setStudent(Student student) {
        return new Submission(assignment, student, status);
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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Submission otherSubmission)) {
            return false;
        }

        return assignment.equals(otherSubmission.assignment) && student.equals(otherSubmission.student)
                        && status.equals(otherSubmission.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assignment, student, status);
    }
}
