package seedu.address.storage.csv;

import java.util.stream.Collectors;

import com.opencsv.bean.CsvBindByName;

import seedu.address.model.student.Student;
import seedu.address.model.tutorial.Tutorial;

/**
 * CSV-friendly version of {@link Student}.
 */
public class CsvAdaptedStudent {

    private static final String TUTORIALS_SEPARATOR = ";"; // Used to separate multiple tutorials in a CSV field

    @CsvBindByName
    private final String name;
    @CsvBindByName
    private final String id;
    @CsvBindByName
    private final String phone;
    @CsvBindByName
    private final String email;
    @CsvBindByName
    private final String handle;
    @CsvBindByName
    private final String tutorials;

    /**
     * Converts a given {@code Student} into this class for CSV use.
     */
    public CsvAdaptedStudent(Student source) {
        this.name = source.getName().fullName;
        this.id = source.getStudentId().id;
        this.phone = source.getPhone().value;
        this.email = source.getEmail().value;
        this.handle = source.getHandle().handle;
        this.tutorials = joinTutorials(source);
    }

    /**
     * Getters
     */
    public String getName() {
        return name;
    }
    public String getId() {
        return id;
    }
    public String getPhone() {
        return phone;
    }
    public String getEmail() {
        return email;
    }
    public String getHandle() {
        return handle;
    }
    public String getTutorials() {
        return tutorials;
    }

    /**
     * Join tutorials by appropriate separator for CSV-friendly list
     */
    public String joinTutorials(Student source) {
        return source.getTutorials().stream().map(Tutorial::toString).collect(Collectors.joining(TUTORIALS_SEPARATOR));
    }

}
