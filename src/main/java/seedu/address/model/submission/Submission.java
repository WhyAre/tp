package seedu.address.model.submission;

import java.util.Date;

import seedu.address.model.student.Student;
import seedu.address.model.tutorial.Assignment;

public record Submission(Student student, Assignment assignment, Date date) {
}
