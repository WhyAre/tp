package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import seedu.address.model.student.Email;
import seedu.address.model.student.Name;
import seedu.address.model.student.Phone;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentID;
import seedu.address.model.student.TelegramHandle;
import seedu.address.model.tutorial.Tutorial;

/**
 * Jackson-friendly version of {@link Student}.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
class JsonAdaptedStudent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Student's %s field is missing!";

    private final String name;
    private final String id;
    private final String phone;
    private final String email;
    private final String handle;
    private final List<JsonAdaptedTutorial> tutorials;

    /**
     * Constructs a {@code JsonAdaptedStudent} with the given student details.
     */
    @JsonCreator
    public JsonAdaptedStudent(@JsonProperty("name") String name, @JsonProperty("id") String studentId,
                    @JsonProperty("phone") String phone, @JsonProperty("email") String email,
                    @JsonProperty("handle") String handle,
                    @JsonProperty("tutorials") List<JsonAdaptedTutorial> tutorials) {
        this.name = name;
        this.id = studentId;
        this.phone = phone;
        this.email = email;
        this.handle = handle;
        if (tutorials != null) {
            this.tutorials = new ArrayList<>(tutorials);
        } else {
            this.tutorials = new ArrayList<>();
        }
    }

    /**
     * Converts a given {@code Student} into this class for Jackson use.
     */
    public JsonAdaptedStudent(Student source) {
        name = source.getName().fullName;
        id = source.getStudentId().id;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        handle = source.getHandle().handle;
        tutorials = source.getTutorials().stream().map(JsonAdaptedTutorial::new).toList();
    }

    /**
     * Converts this Jackson-friendly adapted student object into the model's
     * {@code Student} object.
     */
    public Student toModelType() {
        final Name modelName = new Name(name);
        final StudentID modelStudentId = new StudentID(id);
        final Phone modelPhone = new Phone(phone);
        final Email modelEmail = new Email(email);
        final TelegramHandle modelHandle = new TelegramHandle(handle);
        final Set<Tutorial> modelTutorials = tutorials.stream().map(JsonAdaptedTutorial::toModelType)
                        .collect(Collectors.toCollection(HashSet::new));

        return new Student(modelName, modelStudentId, modelPhone, modelEmail, modelHandle, modelTutorials);
    }

}
