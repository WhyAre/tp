package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import seedu.address.model.tutorial.Tutorial;

/**
 * Jackson-friendly version of {@link Tutorial}.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class JsonAdaptedTutorial {
    private final String name;
    private final List<JsonAdaptedStudent> students;

    JsonAdaptedTutorial(Tutorial tutorial) {
        this(tutorial.name(), tutorial.students().stream().map(JsonAdaptedStudent::new).toList());
    }

    @JsonCreator
    JsonAdaptedTutorial(@JsonProperty("name") String name,
                    @JsonProperty("students") List<JsonAdaptedStudent> students) {
        this.name = name;
        this.students = students;
    }

    /**
     * Converts this Jackson-friendly adapted tutorial object into the model's
     * {@code Tutorial} object.
     */
    public Tutorial toModelType() {
        return new Tutorial(name, students.stream().map(JsonAdaptedStudent::toModelType).collect(Collectors.toCollection(
            ArrayList::new)));
    }
}
