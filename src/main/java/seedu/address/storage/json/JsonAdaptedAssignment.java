package seedu.address.storage.json;

import java.time.LocalDateTime;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.tutorial.Assignment;

/**
 * Jackson-friendly version of {@link Assignment}.
 */
class JsonAdaptedAssignment {

    private final String name;
    private final LocalDateTime dueDate;

    /**
     * Constructs a {@link JsonAdaptedAssignment} with the given assignment details.
     */
    @JsonCreator
    public JsonAdaptedAssignment(@JsonProperty("name") String name, @JsonProperty("dueDate") LocalDateTime dueDate) {
        this.name = name;
        this.dueDate = dueDate;
    }

    /**
     * Converts a given {@link Assignment} into this class for Jackson use.
     */
    public JsonAdaptedAssignment(Assignment source) {
        this(source.name(), source.dueDate().orElse(null));
    }

    /**
     * Converts this Jackson-friendly adapted student object into the model's
     * {@link Assignment} object.
     */
    public Assignment toModelType() {
        return new Assignment(name, Optional.ofNullable(dueDate));
    }

}
