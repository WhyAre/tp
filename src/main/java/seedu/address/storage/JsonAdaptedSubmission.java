package seedu.address.storage;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.student.Student;
import seedu.address.model.submission.Submission;

/**
 * Jackson-friendly version of {@link Student}.
 */
class JsonAdaptedSubmission {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Student's %s field is missing!";

    private final JsonAdaptedStudent student;
    private final JsonAdaptedAssignment assignment;
    private final Date date;

    /**
     * Constructs a {@code JsonAdaptedStudent} with the given student details.
     */
    @JsonCreator
    public JsonAdaptedSubmission(@JsonProperty("student") JsonAdaptedStudent student,
                    @JsonProperty("assignment") JsonAdaptedAssignment assignment, @JsonProperty("date") Date date) {
        this.student = student;
        this.assignment = assignment;
        this.date = date;
    }

    /**
     * Converts a given {@code Student} into this class for Jackson use.
     */
    public JsonAdaptedSubmission(Submission submission) {
        this(new JsonAdaptedStudent(submission.student()), new JsonAdaptedAssignment(submission.assignment()),
                        submission.date());
    }

    /**
     * Converts this Jackson-friendly adapted student object into the model's
     * {@code Student} object.
     *
     * @throws IllegalValueException
     *             if there were any data constraints violated in the adapted
     *             student.
     */
    public Submission toModelType() throws IllegalValueException {
        return new Submission(student.toModelType(), assignment.toModelType(), date);
    }

}
