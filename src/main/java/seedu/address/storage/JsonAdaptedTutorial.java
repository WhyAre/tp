package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tutorial.Tutorial;

/**
 * Jackson-friendly version of {@link Tutorial}.
 */
public class JsonAdaptedTutorial {
    private final String name;
    private final List<JsonAdaptedAssignment> assignments;

    JsonAdaptedTutorial(Tutorial tutorial) {
        this(tutorial.name(), tutorial.assignments().stream().map(JsonAdaptedAssignment::new).toList());
    }

    @JsonCreator
    JsonAdaptedTutorial(@JsonProperty("name") String name,
                    @JsonProperty("assignments") List<JsonAdaptedAssignment> assignments) {
        this.name = name;
        this.assignments = assignments;
    }

    /**
     * Converts this Jackson-friendly adapted tutorial object into the model's
     * {@code Tutorial} object.
     *
     * @throws IllegalValueException
     *             if there were any data constraints violated in the adapted
     *             student.
     */
    public Tutorial toModelType() throws IllegalValueException {
        if (!Tutorial.isValidName(name)) {
            throw new IllegalValueException("Tutorial name is not valid.");
        }

        return new Tutorial(name, assignments.stream().map(JsonAdaptedAssignment::toModelType)
                        .collect(Collectors.toCollection(ArrayList::new)));
    }
}
