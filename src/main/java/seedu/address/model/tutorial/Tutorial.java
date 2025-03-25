package seedu.address.model.tutorial;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import seedu.address.model.student.Student;
import seedu.address.model.uniquelist.Identifiable;

/**
 * Represents a tutorial
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public record Tutorial(@JsonProperty("name") String name, @JsonProperty("students") List<Student> students)
    implements Identifiable<Tutorial> {

    public Tutorial(String name) {
        this(name, new ArrayList<>());
    }

    /**
     * Creates a new Tutorial object
     *
     * @param name name of the tutorial
     */
    public Tutorial {
        if (!isValidName(name)) {
            throw new IllegalArgumentException("Name cannot be empty!");
        }
    }

    /**
     * Checks whether the given name is valid
     */
    public static boolean isValidName(String name) {
        final var pattern = "[a-zA-Z0-9_-]+";

        return name.matches(pattern);
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    @Override
    public boolean hasSameIdentity(Tutorial other) {
        if (other == null) {
            return false;
        }

        return this.name.equals(other.name);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
