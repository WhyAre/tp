package seedu.address.storage;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.student.Student;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Students list contains duplicate student(s).";
    public static final String MESSAGE_DUPLICATE_TUTORIAL = "Tutorials list contains duplicate tutorial(s).";

    private final List<JsonAdaptedStudent> students;
    private final List<JsonAdaptedTutorial> tutorials;
    private final List<JsonAdaptedSubmission> submissions;

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given students and
     * tutorials.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("students") List<JsonAdaptedStudent> students,
                    @JsonProperty("tutorials") List<JsonAdaptedTutorial> tutorials,
                    @JsonProperty("submissions") List<JsonAdaptedSubmission> submissions) {
        this.students = students;
        this.tutorials = tutorials;
        this.submissions = submissions;
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source
     *            future changes to this will not affect the created
     *            {@code JsonSerializableAddressBook}.
     */
    public static JsonSerializableAddressBook of(ReadOnlyAddressBook source) {
        var students = source.getStudentList().stream().map(JsonAdaptedStudent::new).toList();
        var tutorials = source.getTutorialList().stream().map(JsonAdaptedTutorial::new).toList();

        var submissions = source.getSubmissionList().stream().map(JsonAdaptedSubmission::new).toList();

        return new JsonSerializableAddressBook(students, tutorials, submissions);
    }

    /**
     * Sets the tutorials field to the method input
     * <p>
     * This is a method instead of a constructor is for backward-compatibility
     * reasons. It makes the {@code tutorials} key optional in the config file
     */
    @JsonProperty("tutorials")
    public void setTutorials(List<JsonAdaptedTutorial> tutorials) {
        this.tutorials.addAll(tutorials);
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException
     *             if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (var tutorial : tutorials) {
            var t = tutorial.toModelType();
            if (addressBook.hasTutorial(t)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TUTORIAL);
            }
            addressBook.addTutorial(t);
        }
        for (JsonAdaptedStudent jsonAdaptedStudent : students) {
            Student student = jsonAdaptedStudent.toModelType();
            if (addressBook.hasStudent(student)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addStudent(student);
        }
        return addressBook;
    }

}
