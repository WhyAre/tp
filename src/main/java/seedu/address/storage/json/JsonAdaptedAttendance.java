package seedu.address.storage.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.submission.Submission;

/**
 * Jackson-friendly version of {@link Attendance}.
 */
class JsonAdaptedAttendance {

    private final JsonAdaptedTutorial tutorial;
    private final JsonAdaptedStudent student;
    private final List<Integer> attendances;

    /**
     * Constructs a {@link JsonAdaptedAttendance} with the given student details.
     */
    @JsonCreator
    public JsonAdaptedAttendance(@JsonProperty("assignment") JsonAdaptedTutorial tutorial,
                    @JsonProperty("student") JsonAdaptedStudent student,
                    @JsonProperty("attendances") List<Integer> attendences) {
        this.tutorial = tutorial;
        this.student = student;
        this.attendances = attendences;
    }

    /**
     * Converts a given {@link Attendance} into this class for Jackson use.
     */
    public JsonAdaptedAttendance(Attendance source) {
        this(new JsonAdaptedTutorial(source.tutorial()), new JsonAdaptedStudent(source.student()),
                        source.attendances());
    }

    /**
     * Converts this Jackson-friendly adapted student object into the model's
     * {@link Attendance} object.
     */
    public Attendance toModelType() throws IllegalValueException {
        // Get the actual tutorial
        return new Attendance(tutorial.toModelType(), student.toModelType(), attendances);
    }
}
