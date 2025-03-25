package seedu.address.storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.student.Student;
import seedu.address.model.tutorial.Tutorial;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Students list contains duplicate student(s).";
    public static final String MESSAGE_DUPLICATE_TUTORIAL = "Tutorials list contains duplicate tutorial(s).";

    private final List<Student> students = new ArrayList<>();
    private final List<Tutorial> tutorials = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given students and
     * tutorials.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("students") List<Student> students) {
        this.students.addAll(students);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created
     *               {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        students.addAll(source.getStudentList());
        tutorials.addAll(source.getTutorialList());
    }

    private static <T, U> List<U> map(List<T> list, Function<T, U> mapper) {
        return list.stream().map(mapper).toList();
    }

    public static void main(String[] args) throws IOException {
        var str = """
            {
              "students" : [ {
                "@id" : 1,
                "name" : {
                  "fullName" : "John Doe"
                },
                "phone" : {
                  "value" : "98765432"
                },
                "email" : {
                  "value" : "john@example.com"
                },
                "handle" : {
                  "handle" : "@john_doe"
                },
                "studentId" : {
                  "id" : "A0123456Z"
                },
                "tutorials" : [ {
                  "@id" : 2,
                  "name" : "lol",
                  "students" : [ 1 ]
                } ]
              } ],
              "tutorials" : [ 2 ]
            }
            """;
        System.out.println(JsonUtil.fromJsonString(str, JsonSerializableAddressBook.class));
    }

    /**
     * Sets the tutorials field to the method input
     * <p>
     * This is a method instead of a constructor is for backward-compatibility
     * reasons. It makes the {@code tutorials} key optional in the config file
     */
    @JsonProperty("tutorials")
    public void setTutorials(List<Tutorial> tutorials) {
        this.tutorials.addAll(tutorials);
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();

        tutorials.forEach(addressBook::addTutorial);
        students.forEach(addressBook::addStudent);
        return addressBook;
    }
}
