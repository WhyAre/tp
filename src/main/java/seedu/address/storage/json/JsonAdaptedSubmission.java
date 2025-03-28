package seedu.address.storage.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.submission.Submission;
import seedu.address.model.submission.SubmissionStatus;

/**
 * Jackson-friendly version of {@link Submission}.
 */
class JsonAdaptedSubmission {

    private final JsonAdaptedAssignment assignment;
    private final JsonAdaptedStudent student;
    private final SubmissionStatus status;

    /**
     * Constructs a {@code JsonAdaptedStudent} with the given student details.
     */
    @JsonCreator
    public JsonAdaptedSubmission(@JsonProperty("assignment") JsonAdaptedAssignment assignment,
                    @JsonProperty("student") JsonAdaptedStudent student,
                    @JsonProperty("status") SubmissionStatus status) {
        this.assignment = assignment;
        this.student = student;
        this.status = SubmissionStatus.NOT_SUBMITTED;
    }

    /**
     * Converts a given {@link Submission} into this class for Jackson use.
     */
    public JsonAdaptedSubmission(Submission source) {
        this(new JsonAdaptedAssignment(source.assignment()), new JsonAdaptedStudent(source.student()), source.status());
    }

    /**
     * Converts this Jackson-friendly adapted student object into the model's
     * {@link Submission} object.
     */
    public Submission toModelType() throws IllegalValueException {
        return new Submission(assignment.toModelType(), student.toModelType(), status);
    }
}
