package seedu.address.storage;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.student.Student;
import seedu.address.model.tutorial.Assignment;

/**
 * Jackson-friendly version of {@link Student}.
 */
class JsonAdaptedAssignment {

    private final String name;
    private final Date dueDate;

    /**
     * Constructs a {@code JsonAdaptedStudent} with the given student details.
     */
    @JsonCreator
    public JsonAdaptedAssignment(@JsonProperty("name") String name, @JsonProperty("dueDate") Date dueDate) {
        this.name = name;
        this.dueDate = dueDate;
    }

    /**
     * Converts a given {@code Student} into this class for Jackson use.
     */
    public JsonAdaptedAssignment(Assignment source) {
        this(source.name(), source.dueDate());
    }

    /**
     * Converts this Jackson-friendly adapted student object into the model's
     * {@code Student} object.
     *
     * @throws IllegalValueException
     *             if there were any data constraints violated in the adapted
     *             student.
     */
    public Assignment toModelType() {
        return new Assignment(name, dueDate);
    }

}
